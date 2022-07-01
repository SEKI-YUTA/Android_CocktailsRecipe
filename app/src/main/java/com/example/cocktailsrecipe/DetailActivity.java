package com.example.cocktailsrecipe;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
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
    private TextView tv_cocktailName, tv_cocktailGlass,tv_instruction, tv_instructionVideo;
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
        tv_instruction = findViewById(R.id.tv_instruction);
        tv_instructionVideo = findViewById(R.id.tv_instructionVideo);
        recycler_detailInfo = findViewById(R.id.recycler_detailInfo);
//        recycler_ingredient = findViewById(R.id.recycler_ingredient);
//        recycler_measure = findViewById(R.id.recycler_measure);
        recycler_detailInfo.setHasFixedSize(true);
        recycler_detailInfo.setLayoutManager(new GridLayoutManager(this, 1));
        recycler_detailInfo.stopScroll();

//        tv_moreInfo_link.setText(Html.fromHtml(String.format("<a href='%s'>more info...</a>", drink.getStrImageSource())));
//        tv_moreInfo_link.setMovementMethod(LinkMovementMethod.getInstance());
//        tv_moreInfo_link.setAutoLinkMask(1);
        tv_cocktailName.setText(drink.getStrDrink());
        tv_cocktailGlass.setText(drink.getStrGlass());
        tv_instruction.setText(drink.getStrInstructions());
        if(drink.getStrVideo() != null) {
            String link = String.format("<a href='%s'>more info...</a>", drink.getStrVideo());
            Log.d("VideoLink", link);
            tv_instructionVideo.setText(Html.fromHtml(link));
            tv_instructionVideo.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            tv_instructionVideo.setVisibility(View.GONE);
        }

        if(drink.getStrDrinkThumb() != "" && drink.getStrDrinkThumb() != null) {
            Picasso.get().load(drink.getStrDrinkThumb()).into(img_headerImg);
        } else {
            img_headerImg.setImageDrawable(this.getDrawable(R.drawable.cocktail_no_image));
        }

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