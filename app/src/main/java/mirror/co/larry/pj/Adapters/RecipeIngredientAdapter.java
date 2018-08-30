package mirror.co.larry.pj.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mirror.co.larry.pj.Models.Ingredients;
import mirror.co.larry.pj.R;

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.ViewHolder> {

    Context mContext;
    List<Ingredients> ingredientsList;

    public RecipeIngredientAdapter(Context context, List<Ingredients> ingredients){
        mContext = context;
        ingredientsList = ingredients;
    }

    @NonNull
    @Override
    public RecipeIngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recipe_ingredient_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientAdapter.ViewHolder viewHolder, int i) {
        Ingredients ingredients = ingredientsList.get(i);
        viewHolder.mIngredient.setText("Ingredients : " + ingredients.getIngredient());
        viewHolder.mQuantity.setText( "Quantity  : " +  String.valueOf(ingredients.getQuantity()));
        viewHolder.mMeasure.setText("Measure  : " +  ingredients.getMeasure());
    }

    @Override
    public int getItemCount() {
        if(ingredientsList !=null){
            return ingredientsList.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mIngredient, mQuantity, mMeasure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIngredient = itemView.findViewById(R.id.tv_ingredient);
            mQuantity = itemView.findViewById(R.id.tv_quantity);
            mMeasure = itemView.findViewById(R.id.tv_measure);
        }
    }
}
