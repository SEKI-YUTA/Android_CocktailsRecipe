package com.example.cocktailsrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
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

        recycler_favorite.setHasFixedSize(true);
        recycler_favorite.setLayoutManager(new GridLayoutManager(this, 1));

        favoriteCocktailList = dbHelper.getAllFavoriteCocktail();
        adapter = new FavoriteAdapter(this, favoriteCocktailList, listener);

        recycler_favorite.setAdapter(adapter);

    }

    final View.OnLongClickListener listener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            PopupMenu menu = new PopupMenu(FavoriteListActivity.this, view);
            menu.inflate(R.menu.popup_menu);
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.popup_delete:
                            int i = recycler_favorite.indexOfChild(view);
                            dbHelper.deleteFavorite(favoriteCocktailList.get(i));
                            favoriteCocktailList.remove(i);
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return true;
                    }
                }
            });
            menu.show();

            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MyLog", "Resume");
        favoriteCocktailList = dbHelper.getAllFavoriteCocktail();
        adapter = new FavoriteAdapter(this, favoriteCocktailList, listener);
        recycler_favorite.setAdapter(adapter);
    }
}