package com.example.cocktailsrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktailsrecipe.Adapters.IngredientAdapter;
import com.example.cocktailsrecipe.DBModels.FavoriteCocktail;
import com.example.cocktailsrecipe.Models.Drink;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private ImageView img_headerImg;
    private TextView tv_cocktailName, tv_cocktailGlass,tv_instruction, tv_instructionVideo;
    private FloatingActionButton fab_addFavorite;
//    private RecyclerView recycler_ingredient, recycler_measure;
//    private RecyclerView recycler_detailInfo;
    private LinearLayout ingredientArea;
    private DBHelper dbHelper;
    private boolean isFavorite;
//    private List<String> ingredientList = new ArrayList<>();
//    private List<String> measureList = new ArrayList<>();

    private List<Map<String, String>> ingredientsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activirty);

        Drink drink = (Drink) getIntent().getSerializableExtra("drink");

        img_headerImg = findViewById(R.id.img_headerImg);
        tv_cocktailName = findViewById(R.id.tv_cocktailName);
        tv_cocktailGlass = findViewById(R.id.tv_cocktailGlass);
        tv_instruction = findViewById(R.id.tv_instruction);
        tv_instructionVideo = findViewById(R.id.tv_instructionVideo);
        fab_addFavorite = findViewById(R.id.fab_addFavorite);
        ingredientArea = findViewById(R.id.ingredientArea);
//        recycler_detailInfo = findViewById(R.id.recycler_detailInfo);

        dbHelper = DBHelper.getInstance(this);

        isFavorite = dbHelper.alreadyExists(Integer.parseInt(drink.getIdDrink()));
        if(isFavorite) {
            fab_addFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_filled));
        }

        img_headerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, ImageDetailActivity.class);
                intent.putExtra("imgUrl", drink.getStrDrinkThumb());
                startActivity(intent);
            }
        });
        
        fab_addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorite) {
                    Toast.makeText(DetailActivity.this, "Remove Favorite", Toast.LENGTH_SHORT).show();
                    FavoriteCocktail favoriteCocktail = new FavoriteCocktail(Integer.parseInt(drink.getIdDrink()), drink.getStrDrink(), drink.getStrGlass(), drink.getStrDrinkThumb());
                    dbHelper.deleteFavorite(favoriteCocktail);
                    isFavorite = !isFavorite;
                    fab_addFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite));
                } else {
                    Toast.makeText(DetailActivity.this, "Add Favorite", Toast.LENGTH_SHORT).show();
                    FavoriteCocktail favoriteCocktail = new FavoriteCocktail(Integer.parseInt(drink.getIdDrink()), drink.getStrDrink(), drink.getStrGlass(), drink.getStrDrinkThumb());
                    dbHelper.addCocktail(favoriteCocktail);
                    fab_addFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_filled));
                    isFavorite = !isFavorite;
                }
            }
        });

//        recycler_detailInfo.setHasFixedSize(true);
//        recycler_detailInfo.setLayoutManager(new GridLayoutManager(this, 1));
//        recycler_detailInfo.stopScroll();

//        tv_moreInfo_link.setText(Html.fromHtml(String.format("<a href='%s'>more info...</a>", drink.getStrImageSource())));
//        tv_moreInfo_link.setMovementMethod(LinkMovementMethod.getInstance());
//        tv_moreInfo_link.setAutoLinkMask(1);
        tv_cocktailName.setText(drink.getStrDrink());
        tv_cocktailGlass.setText(drink.getStrGlass());
        videoLinkSetUP(drink);

        if(drink.getStrDrinkThumb() != "" && drink.getStrDrinkThumb() != null) {
            Picasso.get().load(drink.getStrDrinkThumb()).into(img_headerImg);
        } else {
            img_headerImg.setImageDrawable(this.getDrawable(R.drawable.cocktail_no_image));
        }

        for(int i = 1; i <= 15; i++) {
            try {
                // ???????????????????????????????????????
                Method methodIngredient = drink.getClass().getMethod("getStrIngredient" + String.valueOf(i));
                Method methodMeasure = drink.getClass().getMethod("getStrMeasure" + String.valueOf(i));
                String ingredient = (String) methodIngredient.invoke(drink);
                String measure = (String) methodMeasure.invoke(drink) == null ? "" : (String) methodMeasure.invoke(drink);
                if(ingredient != "" && ingredient != null) {
                    Log.d("DetailActivity", ingredient);
                    Map<String, String> item = new HashMap<>();
                    item.put(ingredient, measure);

                    LinearLayout ingredientRow = new LinearLayout(this);
                    ingredientRow.setGravity(Gravity.CENTER_VERTICAL);
                    ingredientRow.setWeightSum(3);
                    ingredientRow.setPadding(10, 20, 10, 20);
                    TextView ingredientText = new TextView(this);
                    TextView measureText = new TextView(this);
                    ingredientText.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                    measureText.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));
//                    ingredientText.setPadding(0, 20, 0, 20);
//                    measureText.setPadding(0, 20, 0, 20);
                    ingredientText.setText(ingredient);
                    ingredientText.setTextSize(16);
                    measureText.setText(measure);
                    measureText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    ingredientRow.addView(ingredientText);
                    ingredientRow.addView(measureText);
                    ingredientArea.addView(ingredientRow);

                    ingredientsList.add(item);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

//        ViewGroup.LayoutParams params=recycler_detailInfo.getLayoutParams();
//        params.height=30 * ingredientsList.size() * 3 + 30;
//        Log.d("MyLog", String.valueOf(30 * ingredientsList.size() * 3));
//        recycler_detailInfo.setLayoutParams(params);

//        IngredientAdapter adapter = new IngredientAdapter(this, ingredientsList);
//        recycler_detailInfo.setAdapter(adapter);
    }
    // ?????????????????????????????????????????????????????????????????????
    private void videoLinkSetUP(Drink drink) {
        tv_instruction.setText(drink.getStrInstructions());
        if(drink.getStrVideo() != null) {
            String link = String.format("<a href='%s'>more info...</a>", drink.getStrVideo());
            Log.d("VideoLink", link);
            tv_instructionVideo.setText(Html.fromHtml(link));
            tv_instructionVideo.setMovementMethod(LinkMovementMethod.getInstance());
            tv_instructionVideo.setVisibility(View.VISIBLE);
        } else {
            tv_instructionVideo.setVisibility(View.GONE);
        }
    }
}