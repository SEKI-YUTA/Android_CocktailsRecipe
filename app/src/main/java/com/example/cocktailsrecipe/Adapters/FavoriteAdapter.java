package com.example.cocktailsrecipe.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktailsrecipe.CocktailsAPI;
import com.example.cocktailsrecipe.DBHelper;
import com.example.cocktailsrecipe.DBModels.FavoriteCocktail;
import com.example.cocktailsrecipe.DetailActivity;
import com.example.cocktailsrecipe.Models.CocktailsResponse;
import com.example.cocktailsrecipe.R;
import com.example.cocktailsrecipe.ViewHolders.CocktailViewHolder;
import com.squareup.picasso.Picasso;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoriteAdapter extends RecyclerView.Adapter<CocktailViewHolder> {
    private Context context;
    private List<FavoriteCocktail> favoriteCocktailList;
    private View.OnLongClickListener listener;
    private Retrofit retrofit;

    public FavoriteAdapter(Context context, List<FavoriteCocktail> favoriteCocktailList, View.OnLongClickListener listener) {
        this.context = context;
        this.favoriteCocktailList = favoriteCocktailList;
        this.listener = listener;

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CocktailViewHolder(LayoutInflater.from(context).inflate(R.layout.cocktail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailViewHolder holder, int position) {
        FavoriteCocktail favoriteCocktail = favoriteCocktailList.get(holder.getAdapterPosition());
        holder.tv_cocktailName.setText(favoriteCocktail.getName());
        holder.tv_cocktailGlass.setText(favoriteCocktail.getGlass());

        Picasso.get().load(favoriteCocktail.getImageUrl()).into(holder.img_cocktailThumbnail);

        holder.card_cocktail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // IDから詳細データを取得して詳細画面へ遷移
                CocktailsAPI cocktailsAPI = retrofit.create(CocktailsAPI.class);
                Call<CocktailsResponse> call = cocktailsAPI.getCocktailByID(String.valueOf(favoriteCocktail.getId()));
                call.enqueue(new Callback<CocktailsResponse>() {
                    @Override
                    public void onResponse(Call<CocktailsResponse> call, Response<CocktailsResponse> response) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("drink", response.body().getDrinks().get(0));
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<CocktailsResponse> call, Throwable t) {
                        Toast.makeText(context, "can not get data\nplease retry later", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.card_cocktail.setOnLongClickListener(listener);

    }

    @Override
    public int getItemCount() {
        return favoriteCocktailList.size();
    }
}
