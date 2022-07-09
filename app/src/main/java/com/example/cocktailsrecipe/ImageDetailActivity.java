package com.example.cocktailsrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

public class ImageDetailActivity extends AppCompatActivity {
//    ImageView img_detailBig;
    TouchImageView img_detailBig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        String imgUrl = getIntent().getStringExtra("imgUrl");

        img_detailBig = findViewById(R.id.img_detailBig);

        Picasso.get().load(imgUrl).into(img_detailBig);
    }
}