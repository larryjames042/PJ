package mirror.co.larry.pj.UI;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import mirror.co.larry.pj.Adapters.RecipeAdapter;
import mirror.co.larry.pj.Fragments.RecipeIngredientFragment;
import mirror.co.larry.pj.Fragments.RecipeStepDetailFragment;
import mirror.co.larry.pj.Models.Ingredients;
import mirror.co.larry.pj.Models.Recipe;
import mirror.co.larry.pj.Models.Steps;
import mirror.co.larry.pj.R;

public class RecipeStepDetailActivity extends AppCompatActivity implements RecipeStepDetailFragment.OnFragmentInteractionListener, RecipeIngredientFragment.OnFragmentInteractionListener{
    private static final String TAG = RecipeStepDetailActivity.class.getSimpleName();
    Steps steps;
    List<Ingredients> ingredientsList;
    Recipe recipe;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        if(savedInstanceState==null){
            Bundle bundle= null;
            String videoUrl="", des="", thumbnailUrl="";
            if(getIntent().hasExtra(RecipeAdapter.INGREDIENT_KEY_EXTRA)){
                bundle = getIntent().getBundleExtra(RecipeAdapter.INGREDIENT_KEY_EXTRA);
            }

            if(getIntent().hasExtra(RecipeAdapter.KEY_STEPS_EXTRA)){
                bundle = getIntent().getBundleExtra(RecipeAdapter.KEY_STEPS_EXTRA);
            }


            if(bundle!=null){
                if(bundle.containsKey(RecipeAdapter.INGREDIENT_KEY)){
                    recipe = (Recipe) bundle.getSerializable(RecipeAdapter.INGREDIENT_KEY);
                    ingredientsList = recipe.getIngredients();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.recipe_detail_step_container, RecipeIngredientFragment.newInstance(ingredientsList), null).commit();
                }

                if(bundle.containsKey(RecipeAdapter.KEY_STEPS)){
                    steps = (Steps) bundle.getSerializable(RecipeAdapter.KEY_STEPS);
//                position = bundle.getInt(RecipeAdapter.POSITION_KEY);
                    videoUrl = steps.getVideoURL();
                    des = steps.getShortDescription();
                    thumbnailUrl = steps.getThumbnailURL();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.recipe_detail_step_container, RecipeStepDetailFragment.newInstance(position ,videoUrl,thumbnailUrl,des), null).commit();

                }


            }

        }



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
