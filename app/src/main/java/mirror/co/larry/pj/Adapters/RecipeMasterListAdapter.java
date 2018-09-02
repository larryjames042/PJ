package mirror.co.larry.pj.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import mirror.co.larry.pj.Fragments.RecipeDetailFragment;
import mirror.co.larry.pj.Models.Ingredients;
import mirror.co.larry.pj.Models.Recipe;
import mirror.co.larry.pj.Models.Steps;
import mirror.co.larry.pj.R;
import mirror.co.larry.pj.databinding.RecipeDetailIngradientHeaderListItemBinding;
import mirror.co.larry.pj.databinding.RecipeDetailListItemBinding;

public class RecipeMasterListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_HEADER_TYPE_INGREDIENT = 1;
    private static final int ITEM_TYPE_RECIPE = 2;
    private List<Object> list = new ArrayList<>();
    RecipeDetailFragment.OnItemClickListener mListener;
    List<Steps>  stepsList;
    List<Ingredients> ingredientsList;
    Recipe mRecipe;
    Context mContext;

    public RecipeMasterListAdapter(Context context, Recipe recipe, RecipeDetailFragment.OnItemClickListener mListener){
        mContext = context;
        mRecipe = recipe;
        this.mListener = mListener;
        stepsList = mRecipe.getSteps();
        ingredientsList = mRecipe.getIngredients();
        list.add(ingredientsList.get(0));
        list.addAll(stepsList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layout = 0;

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());


        RecyclerView.ViewHolder viewHolder;

        switch (i){
            case ITEM_HEADER_TYPE_INGREDIENT:
                layout = R.layout.recipe_detail_ingradient_header_list_item;
                RecipeDetailIngradientHeaderListItemBinding bindingHeader = DataBindingUtil.inflate(layoutInflater, layout, viewGroup, false);
                viewHolder = new ViewHolder1(bindingHeader);
                break;
            case ITEM_TYPE_RECIPE:
                layout = R.layout.recipe_detail_list_item;
                RecipeDetailListItemBinding  binding  = DataBindingUtil.inflate(layoutInflater, layout, viewGroup, false);
                viewHolder = new ViewHolder2(binding);
                break;
                default:
                    viewHolder = null;
                    break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        int viewType = viewHolder.getItemViewType();

        switch (viewType){
            case ITEM_HEADER_TYPE_INGREDIENT:
                final Ingredients ingredients = (Ingredients) list.get(i);
                ((ViewHolder1)viewHolder).binding.tvRecipeIngredientTitleName.setText("Ingredients");
                ((ViewHolder1)viewHolder).binding.tvRecipeIngredientTitleName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onItemSelected(i, stepsList, ingredientsList);
                    }
                });
                break;
            case ITEM_TYPE_RECIPE:
                Steps step = (Steps)list.get(i);
                ((ViewHolder2)viewHolder).binding.tvRecipeDetailName.setText(String.valueOf(i)+". " + step.getShortDescription());
                ((ViewHolder2)viewHolder).binding.tvRecipeDetailName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onItemSelected(i, stepsList, ingredientsList);
                    }
                });
                break;
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof Ingredients){
            return ITEM_HEADER_TYPE_INGREDIENT;
        }else if(list.get(position) instanceof Steps){
            return ITEM_TYPE_RECIPE;
        }

        return -1;
    }



    public class ViewHolder1 extends RecyclerView.ViewHolder{

        public RecipeDetailIngradientHeaderListItemBinding binding;

        public ViewHolder1(RecipeDetailIngradientHeaderListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder{

        public RecipeDetailListItemBinding binding;

        public ViewHolder2(RecipeDetailListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

        }
    }
}


//public class RecipeMasterListAdapter extends RecyclerView.Adapter<RecipeMasterListAdapter.ViewHolder>{
//    private static final int ITEM_HEADER_TYPE_INGREDIENT = 1;
//    private static final int ITEM_TYPE_RECIPE = 2;
//    private List<Object> list = new ArrayList<>();
//    RecipeDetailFragment.OnItemClickListener mListener;
//    List<Steps>  stepsList;
//    List<Ingredients> ingredientsList;
//    Recipe mRecipe;
//
//    Context mContext;
//
//    public RecipeMasterListAdapter(Context context, Recipe recipe, RecipeDetailFragment.OnItemClickListener mListener){
//        mContext = context;
//        mRecipe = recipe;
//        this.mListener = mListener;
//        stepsList = mRecipe.getSteps();
//        ingredientsList = mRecipe.getIngredients();
//        list.add(ingredientsList.get(0));
//        list.addAll(stepsList);
//    }
//
//    @NonNull
//    @Override
//    public RecipeMasterListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
//        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
//
//        RecipeDetailListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.recipe_detail_list_item, viewGroup, false);
//        return new ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecipeMasterListAdapter.ViewHolder viewHolder, int i) {
////        Steps step = (Steps)list.get(i);
////        viewHolder.binding.tvRecipeDetailName.setText(step.getShortDescription());
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public RecipeDetailListItemBinding binding;
//
//        public ViewHolder(@NonNull RecipeDetailListItemBinding itemBinding) {
//            super(itemBinding.getRoot());
//            this.binding = itemBinding;
//        }
//    }
//}