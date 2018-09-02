package mirror.co.larry.pj.Adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import mirror.co.larry.pj.databinding.RecipeListItemBinding;

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
    // public constructor
    public RecipeAdapter(Context context, List<Recipe> list){
        mContext = context;
        mRecipeList = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

       LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
       RecipeListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.recipe_list_item, viewGroup, false);

       return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
//        final Recipe recipe = mRecipeList.get(i);
        viewHolder.binding.setRecipe(mRecipeList.get(i));
        viewHolder.binding.tvRecipeName.setText(mRecipeList.get(i).getName());
        viewHolder.binding.tvRecipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(RECIPE_KEY, viewHolder.binding.getRecipe());
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
        private RecipeListItemBinding binding;

        public ViewHolder(RecipeListItemBinding itemBinding ) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
