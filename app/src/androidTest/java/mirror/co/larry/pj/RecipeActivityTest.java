package mirror.co.larry.pj;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import mirror.co.larry.pj.UI.RecipeActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {


   @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule
           = new ActivityTestRule(RecipeActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource(){
        idlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

   @Test
    public void click_recyclerview_open_recipeDetailActivity(){
      onView(withId(R.id.rv_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
       onView(withId(R.id.rv_master_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
       onView(withId(R.id.tv_description)).check(matches(withText("Recipe Introduction")));
    }

   @After
    public void unRegisterIdlingResource(){
        if(idlingResource!=null){
            Espresso.unregisterIdlingResources(idlingResource);
        }
   }

}
