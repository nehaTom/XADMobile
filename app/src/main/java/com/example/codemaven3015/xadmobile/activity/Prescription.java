package com.example.codemaven3015.xadmobile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.codemaven3015.xadmobile.R;
import com.squareup.picasso.Picasso;

public class Prescription extends AppCompatActivity {
ImageView description_imv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(getString(R.string.title_description));
        toolbar.setNavigationIcon(R.drawable.arrow);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


        description_imv=findViewById(R.id.description_imv);



        String photo=getIntent().getStringExtra("imvUrl");
        Log.e("Imageurl",""+photo);
        Picasso.with(this)
                .load(photo)
                .resize(300, 300)
                .centerInside()
                .into(description_imv);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prescription.super.onBackPressed();
            }
        });
    }
}
