package com.example.cocktailsrecipe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktailsrecipe.R;
import com.example.cocktailsrecipe.ViewHolders.IngredientViewHolder;

import java.util.List;
import java.util.Map;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {
    private Context context;
    private List<Map<String,String>> ingredientList;

    public IngredientAdapter(Context context, List<Map<String, String>> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Map<String, String> item = ingredientList.get(holder.getAdapterPosition());
        for(Map.Entry<String, String> entry: item.entrySet()) {
            holder.tv_ingredient.setText(entry.getKey());
            holder.tv_measure.setText(entry.getValue());
        }
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
}
