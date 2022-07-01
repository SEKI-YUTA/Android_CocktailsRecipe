package com.example.cocktailsrecipe;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktailsrecipe.Adapters.IngredientAdapter;
import com.example.cocktailsrecipe.Models.Drink;
import com.squareup.picasso.Picasso;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private ImageView img_headerImg;
    private TextView tv_cocktailName, tv_cocktailGlass;
//    private RecyclerView recycler_ingredient, recycler_measure;
    private RecyclerView recycler_detailInfo;
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
        recycler_detailInfo = findViewById(R.id.recycler_detailInfo);
//        recycler_ingredient = findViewById(R.id.recycler_ingredient);
//        recycler_measure = findViewById(R.id.recycler_measure);
        recycler_detailInfo.setHasFixedSize(true);
        recycler_detailInfo.setLayoutManager(new GridLayoutManager(this, 1));
        recycler_detailInfo.stopScroll();

        Picasso.get().load(drink.getStrDrinkThumb()).into(img_headerImg);
        tv_cocktailName.setText(drink.getStrDrink());
        tv_cocktailGlass.setText(drink.getStrGlass());

        for(int i = 1; i <= 15; i++) {
            try {
                // リフレクションを使っている
                Method methodIngredient = drink.getClass().getMethod("getStrIngredient" + String.valueOf(i));
                Method methodMeasure = drink.getClass().getMethod("getStrMeasure" + String.valueOf(i));
                String ingredient = (String) methodIngredient.invoke(drink);
                String measure = (String) methodMeasure.invoke(drink) == null ? "" : (String) methodMeasure.invoke(drink);
                if(ingredient != "" && ingredient != null) {
                    Log.d("DetailActivity", ingredient);
                    Map<String, String> item = new HashMap<>();
                    item.put(ingredient, measure);
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

        IngredientAdapter adapter = new IngredientAdapter(this, ingredientsList);
        recycler_detailInfo.setAdapter(adapter);
    }
}