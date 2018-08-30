package mirror.co.larry.pj.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import mirror.co.larry.pj.Adapters.RecipeAdapter;
import mirror.co.larry.pj.Fragments.RecipeDetailFragment;
import mirror.co.larry.pj.Fragments.RecipeIngredientFragment;
import mirror.co.larry.pj.Fragments.RecipeStepDetailFragment;
import mirror.co.larry.pj.Models.Ingredients;
import mirror.co.larry.pj.Models.Recipe;
import mirror.co.larry.pj.Models.Steps;
import mirror.co.larry.pj.R;

public class MainActivity extends AppCompatActivity implements RecipeDetailFragment.OnItemClickListener {

    List<Steps> stepsList;
    List<Ingredients> ingredientsList;
    Recipe recipe;
    private boolean isTwoPane;
    private int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        recipe=null;
        Bundle b = getIntent().getBundleExtra(RecipeAdapter.RECIPE_KEY_EXTRA);
        if(b!=null){
            recipe =(Recipe) b.getSerializable(RecipeAdapter.RECIPE_KEY);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.recipe_detail_container, RecipeDetailFragment.newInstance(recipe)).commit();

        isTwoPane = findViewById(R.id.step_detail_linear_layout) != null;





    }

    @Override
    public void onItemSelected(int position, List<Steps> stepsList, List<Ingredients> ingredientsList) {
        mPosition = position;
        if(isTwoPane){
            if(mPosition==0){
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.recipe_detail_step_container, RecipeIngredientFragment.newInstance(ingredientsList), null).commit();
            }else{
                String videoUrl="", des="", thumbnailUrl="";
                videoUrl = stepsList.get(mPosition).getVideoURL();
                des = stepsList.get(mPosition).getShortDescription();
                thumbnailUrl = stepsList.get(mPosition).getThumbnailURL();

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.recipe_detail_step_container, RecipeStepDetailFragment.newInstance(mPosition,videoUrl,thumbnailUrl,des), null).commit();
            }
        }else{

            Intent intent = new Intent(MainActivity.this, RecipeStepDetailActivity.class);
            if(mPosition==0){
                Bundle b = new Bundle();
                b.putSerializable(RecipeAdapter.INGREDIENT_KEY, recipe);
                intent.putExtra(RecipeAdapter.INGREDIENT_KEY_EXTRA,b);
                startActivity(intent);
            }else{
                int actualPosition = mPosition-1;
                Steps steps = new Steps(stepsList.get(actualPosition).getId(),
                        stepsList.get(actualPosition).getShortDescription(),
                        stepsList.get(actualPosition).getVideoURL(),
                        stepsList.get(actualPosition).getThumbnailURL());

                Bundle b = new Bundle();
//                b.putInt( RecipeAdapter.POSITION_KEY ,actualPosition);
                b.putSerializable(RecipeAdapter.KEY_STEPS, steps);
                intent.putExtra(RecipeAdapter.KEY_STEPS_EXTRA,b);
                startActivity(intent);
            }
        }

    }


//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
//
//    @Override
//    public void onNextButtonClick(int position) {
//        if(position<=)
//        mPosition = position;
//    }
//
//    @Override
//    public void onBackButtonClick(int position) {
//        mPosition = position;
//    }
}
