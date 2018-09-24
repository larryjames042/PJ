package mirror.co.larry.pj.UI;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mirror.co.larry.pj.Adapters.RecipeAdapter;
import mirror.co.larry.pj.Api.RecipeApi;
import mirror.co.larry.pj.Api.RecipeService;
import mirror.co.larry.pj.IdlingResource.SimpleIdlingResource;
import mirror.co.larry.pj.Models.Recipe;
import mirror.co.larry.pj.NetworkUtil.NetworkUtils;
import mirror.co.larry.pj.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    RecipeService recipeService;
    List<Recipe> recipeList;
    Gson gson;
    RecyclerView recipeRecyclerView;
    RecipeAdapter recipeAdapter;

    SimpleIdlingResource idlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        // init and setting up recyclerview
        recipeRecyclerView = findViewById(R.id.rv_recipe);
        if(getApplicationContext().getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }else{
            recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        recipeList = new ArrayList<>();
        gson = new Gson();
        recipeService = RecipeApi.getClient().create(RecipeService.class);

        getIdlingResource();
        if(idlingResource!=null){
            idlingResource.setIdleState(false);
        }

        if(NetworkUtils.isConnected(this)){
            // making a request to server
            callRecipeApi().enqueue(new Callback<List<Recipe>>() {

                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                    recipeList.addAll(response.body());
                    List<Recipe> recipes = new ArrayList<>();

                    for(Recipe recipe: recipeList){
                        recipes.add(recipe);
                    }

                    recipeAdapter = new RecipeAdapter(RecipeActivity.this, recipes);
                    recipeRecyclerView.setAdapter(recipeAdapter);
                    if(idlingResource!=null){
                        idlingResource.setIdleState(true);
                    }


                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Snackbar.make(getWindow().getDecorView().getRootView() , "Check your Internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    private Call<List<Recipe>> callRecipeApi(){
        return recipeService.getRecipe();
    }


}
