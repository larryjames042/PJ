package mirror.co.larry.pj.Api;

import com.google.gson.JsonArray;

import java.util.List;

import mirror.co.larry.pj.Models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipe();
}
