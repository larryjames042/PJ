package mirror.co.larry.pj.Service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.List;

import mirror.co.larry.pj.Adapters.RecipeAdapter;
import mirror.co.larry.pj.IngredientWidgetProvider;
import mirror.co.larry.pj.Models.Ingredients;
import mirror.co.larry.pj.Models.Recipe;
import mirror.co.larry.pj.Models.Steps;
import mirror.co.larry.pj.R;

public class IngredientService extends IntentService {

    public static final String ACTION_ADD_INGREDIENT = "action_add_ingredient";

    public IngredientService() {
        super("IngredientService");
    }



    public static  void startActionAddIngredient(Context context, Recipe b){
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(ACTION_ADD_INGREDIENT);
        intent.putExtra("recipe", b);
        context.startService(intent);
    }

    private void handleActionAddIngredient(List<Ingredients> list, String name) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds  = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_text);
        IngredientWidgetProvider.updateIngredientWidget(this, appWidgetManager, list,name, appWidgetIds);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent!=null){
            final String action = intent.getAction();

            if(ACTION_ADD_INGREDIENT.equals(action)){
                if(intent.hasExtra("recipe")){
//                    final Bundle b = intent.getBundleExtra(RecipeAdapter.RECIPE_KEY_EXTRA);
//                     Recipe recipe = (Recipe) b.getSerializable(RecipeAdapter.RECIPE_KEY);
                    Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");
                    List<Ingredients> ingredientsList = recipe.getIngredients();
                    String recipeName = recipe.getName().toString();
                    handleActionAddIngredient(ingredientsList, recipeName);
                }
            }
        }
    }


}
