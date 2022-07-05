package com.example.cocktailsrecipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cocktailsrecipe.Adapters.FavoriteAdapter;
import com.example.cocktailsrecipe.DBModels.FavoriteCocktail;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String FAVORITE_COCKTAIL_TABLE = "FOVORITE_COCKTAIL_TABLE";
    public static final String COCKTAIL_ID = "COCKTAIL_ID";
    public static final String COCKTAIL_NAME = "COCKTAIL_NAME";
    public static final String COCKTAIL_GLASS = "COCKTAIL_GLASS";
    public static final String COCKTAIL_IMAGE_URL = "COCKTAIL_IMAGE_URL";

    public DBHelper(@Nullable Context context) {
        super(context.getApplicationContext(), "cocktailApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + FAVORITE_COCKTAIL_TABLE + " (" + COCKTAIL_ID + " INTEGER PRIMARY KEY, " + COCKTAIL_NAME + " TEXT, " + COCKTAIL_GLASS + " TEXT, " + COCKTAIL_IMAGE_URL + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addCocktail(FavoriteCocktail favoriteCocktail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COCKTAIL_ID, favoriteCocktail.getId());
        cv.put(COCKTAIL_NAME, favoriteCocktail.getName());
        cv.put(COCKTAIL_GLASS, favoriteCocktail.getGlass());
        cv.put(COCKTAIL_IMAGE_URL, favoriteCocktail.getImageUrl());

        long insert = db.insert(FAVORITE_COCKTAIL_TABLE, null ,cv);
//        db.close();
        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<FavoriteCocktail> getAllFavoriteCocktail() {
        List<FavoriteCocktail> favoriteCocktailList = new ArrayList<>();

        String queryString = "SELECT * FROM " + FAVORITE_COCKTAIL_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int cocktailID = cursor.getInt(0);
                String cocktailName = cursor.getString(1);
                String cocktailGlass = cursor.getString(2);
                String cocktailImageUrl = cursor.getString(3);

                FavoriteCocktail favoriteCocktail = new FavoriteCocktail(cocktailID, cocktailName, cocktailGlass, cocktailImageUrl);

                favoriteCocktailList.add(favoriteCocktail);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteCocktailList;
    }


    public boolean deleteFavorite(FavoriteCocktail favoriteCocktail) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + FAVORITE_COCKTAIL_TABLE + " WHERE " + COCKTAIL_ID + " = " + favoriteCocktail.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }
    }
}
