package com.example.cocktailsrecipe.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktailsrecipe.DetailActivity;
import com.example.cocktailsrecipe.Models.Drink;
import com.example.cocktailsrecipe.R;
import com.example.cocktailsrecipe.ViewHolders.CocktailViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailViewHolder> {
    private Context context;
    private List<Drink> drinkList;

    public CocktailAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CocktailViewHolder(LayoutInflater.from(context).inflate(R.layout.cocktail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailViewHolder holder, int position) {
        Drink drink = drinkList.get(holder.getAdapterPosition());
        holder.tv_cocktailName.setText(drink.getStrDrink());
        holder.tv_cocktailGlass.setText(drink.getStrGlass());
        holder.card_cocktail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("drink", drink);
                context.startActivity(intent);
            }
        });
        holder.tv_cocktailName.setSelected(true);
        if(drink.getStrDrinkThumb() != "" && drink.getStrDrinkThumb() != null) {
            Picasso.get().load(drink.getStrDrinkThumb()).into(holder.img_cocktailThumbnail);
        } else {
            holder.img_cocktailThumbnail.setImageDrawable(context.getDrawable(R.drawable.cocktail_no_image));
        }
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
