package com.example.cocktailsrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cocktailsrecipe.Adapters.FavoriteAdapter;
import com.example.cocktailsrecipe.DBModels.FavoriteCocktail;

import java.util.List;

public class FavoriteListActivity extends AppCompatActivity {
    private RecyclerView recycler_favorite;
    private DBHelper dbHelper;
    private FavoriteAdapter adapter;
    List<FavoriteCocktail> favoriteCocktailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        recycler_favorite = findViewById(R.id.recycler_favorite);
        dbHelper = new DBHelper(this);

        favoriteCocktailList = dbHelper.getAllFavoriteCocktail();

        recycler_favorite.setHasFixedSize(true);
        recycler_favorite.setLayoutManager(new GridLayoutManager(this, 1));

        adapter = new FavoriteAdapter(this, favoriteCocktailList, listener);

        recycler_favorite.setAdapter(adapter);

    }

    final View.OnLongClickListener listener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            int i = recycler_favorite.indexOfChild(view);
            dbHelper.deleteFavorite(favoriteCocktailList.get(i));
            favoriteCocktailList.remove(i);
            adapter.notifyDataSetChanged();
            return false;
        }
    };
}