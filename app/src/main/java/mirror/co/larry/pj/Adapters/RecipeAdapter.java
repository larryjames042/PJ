package mirror.co.larry.pj.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mirror.co.larry.pj.Models.Ingredients;
import mirror.co.larry.pj.Models.Recipe;
import mirror.co.larry.pj.Models.Steps;
import mirror.co.larry.pj.R;
import mirror.co.larry.pj.UI.MainActivity;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    public static final String RECIPE_KEY = "recipe";
    public static final String INGREDIENT_KEY = "ingredients";
    public static final String INGREDIENT_KEY_EXTRA = "ingredients_extra";
    public static final String POSITION_KEY = "key";
    public static final String RECIPE_KEY_EXTRA = "recipe_extra";
    public static final String KEY_STEPS_EXTRA = "extra_step_key";
    public static final String KEY_STEPS = "key_step";

    Context mContext;
    List<Recipe> mRecipeList;
    List<Steps> mSteps;
    List<Ingredients> mIngredients;
    // public constructor
    public RecipeAdapter(Context context, List<Recipe> list){
        mContext = context;
        mRecipeList = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Recipe recipe = mRecipeList.get(i);
        viewHolder.itemView.setTag(recipe.getId());
        viewHolder.mRecipeName.setText(recipe.getName().toString());
        viewHolder.mRecipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                  mIngredients = recipe.getIngredients();
//                  mSteps = recipe.getSteps();

                Intent i = new Intent(mContext, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(RECIPE_KEY, recipe);
//                bundle.putInt(RECIPE_ID_KEY, recipe.getId());
//                bundle.putInt(RECIPE_SERVING_KEY, recipe.getServings());
//                bundle.putString(RECIPE_IMAGE_KEY, recipe.getImage());
//                bundle.putSerializable(INGREDIENT_KEY,  (Serializable) mIngredients);
//                bundle.putSerializable(STEPS_KEY, (Serializable) mSteps);
                i.putExtra(RECIPE_KEY_EXTRA, bundle);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mRecipeList!=null){
            return mRecipeList.size();
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mRecipeName;
        public ViewHolder(View itemView ) {
            super(itemView);
            mRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        }
    }
}
