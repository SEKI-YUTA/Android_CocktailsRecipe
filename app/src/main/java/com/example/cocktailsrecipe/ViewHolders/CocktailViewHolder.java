package com.example.cocktailsrecipe.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktailsrecipe.R;

public class CocktailViewHolder extends RecyclerView.ViewHolder{
    public TextView tv_cocktailName, tv_cocktailGlass;
    public ImageView img_cocktailThumbnail;
    public CardView card_cocktail;
    public CocktailViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_cocktailName = itemView.findViewById(R.id.tv_cocktailName);
        tv_cocktailGlass = itemView.findViewById(R.id.tv_cocktailGlass);
        img_cocktailThumbnail = itemView.findViewById(R.id.img_cocktailThumbnail);
        card_cocktail = itemView.findViewById(R.id.card_cocktail);
    }
}
