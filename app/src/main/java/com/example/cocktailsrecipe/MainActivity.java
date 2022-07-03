package com.example.cocktailsrecipe;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktailsrecipe.Adapters.CocktailAdapter;
import com.example.cocktailsrecipe.Models.CocktailsResponse;
import com.example.cocktailsrecipe.Models.Drink;
import com.example.cocktailsrecipe.Utils.GeneralUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText edit_keyword;
    private ImageButton imgBtn_search;
    private RecyclerView recyclerCocktails;
    private Retrofit retrofit;
    private List<Drink> drinkList = new ArrayList<>();
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MyLog", "OnCreate");

        CocktailAdapter adapter = new CocktailAdapter(MainActivity.this, drinkList);

        Drawable drawable = getDrawable(R.drawable.ic_search);
        drawable.setTint(getResources().getColor(R.color.white));
        drawable.setTintMode(PorterDuff.Mode.SRC_IN);

        edit_keyword = findViewById(R.id.edit_keyword);
        imgBtn_search = findViewById(R.id.imgBtn_search);
        recyclerCocktails = findViewById(R.id.recycler_cocktails);
        recyclerCocktails.setHasFixedSize(true);
        recyclerCocktails.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerCocktails.setAdapter(adapter);

        imgBtn_search.setImageDrawable(drawable);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Searching...\nwait a moment");

        Dexter.withContext(this).withPermission(Manifest.permission.INTERNET)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        appSetUP();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "INTERNET permission is required", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    // パーミッションが許可されたときの処理
    private void appSetUP() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 最初に表示させるランダムなカクテルを取得
        CocktailsAPI cocktailsAPI = retrofit.create(CocktailsAPI.class);
        Call<CocktailsResponse> call = cocktailsAPI.getRandomCocktail();
        call.enqueue(new Callback<CocktailsResponse>() {
            @Override
            public void onResponse(Call<CocktailsResponse> call, Response<CocktailsResponse> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "invalid data", Toast.LENGTH_SHORT).show();
                }
                drinkList  = response.body().getDrinks();
                for (Drink drink: drinkList) {
                    Log.d("MyLog", drink.getStrDrink());
                }
                CocktailAdapter adapter = new CocktailAdapter(MainActivity.this, drinkList);
                recyclerCocktails.setAdapter(adapter);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<CocktailsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Con not get data from api", Toast.LENGTH_SHORT).show();
            }
        });
        // 最初に表示させるランダムなカクテルを取得　ここまで-------

        imgBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_keyword.getText().toString() == "") {
                    Toast.makeText(MainActivity.this, "please enter any keyword", Toast.LENGTH_SHORT).show();
                    return;
                }
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                edit_keyword.clearFocus();

                dialog.show();

                CocktailsAPI cocktailsAPI = retrofit.create(CocktailsAPI.class);
                Call<CocktailsResponse> call = cocktailsAPI.searchCocktails("1", edit_keyword.getText().toString());
                call.enqueue(new Callback<CocktailsResponse>() {
                    @Override
                    public void onResponse(Call<CocktailsResponse> call, Response<CocktailsResponse> response) {
                        if(!response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "invalid data", Toast.LENGTH_SHORT).show();
                        }
                        drinkList  = response.body().getDrinks();
                        for (Drink drink: drinkList) {
                            Log.d("MyLog", drink.getStrDrink());
                        }
                        CocktailAdapter adapter = new CocktailAdapter(MainActivity.this, drinkList);
                        recyclerCocktails.setAdapter(adapter);
                        dialog.hide();
                    }

                    @Override
                    public void onFailure(Call<CocktailsResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Con not get data from api", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(GeneralUtil.getInstance().isDoubleTapped(new Date().getTime())) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Quickly double tap to go back homeScreen", Toast.LENGTH_SHORT).show();
        }
    }
}