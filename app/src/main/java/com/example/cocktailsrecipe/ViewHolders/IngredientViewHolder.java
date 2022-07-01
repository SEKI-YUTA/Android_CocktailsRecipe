package com.example.cocktailsrecipe.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktailsrecipe.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_ingredient, tv_measure;
    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_ingredient = itemView.findViewById(R.id.tv_ingredient);
        tv_measure = itemView.findViewById(R.id.tv_measure);
    }
}
