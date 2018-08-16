package mirror.co.larry.pj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import mirror.co.larry.pj.Api.RecipeApi;
import mirror.co.larry.pj.Api.RecipeService;
import mirror.co.larry.pj.Models.Ingredient;
import mirror.co.larry.pj.Models.Recipe;
import mirror.co.larry.pj.Models.Steps;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecipeService recipeService;
    TextView responseString;
    List<Recipe> recipeList;
    List<Ingredient> ingredientsList;
    List<Steps> stepsList;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseString = findViewById(R.id.tv_response);
        gson = new Gson();
        recipeService = RecipeApi.getClient().create(RecipeService.class);
        callRecipeApi().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList = (List<Recipe>)response.body();

                Toast.makeText(getApplicationContext(), "successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private Call<List<Recipe>> callRecipeApi(){
        return recipeService.getRecipe();
    }

    public void nextActivity(View view){
        Intent i = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(i);
    }
}
