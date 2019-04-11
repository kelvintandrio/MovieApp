package com.example.kelvin.movieapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView Title = findViewById(R.id.isi_judul);
        TextView Description = findViewById(R.id.isi_deskripsi);
        TextView Date = findViewById(R.id.isi_tanggal);
        ImageView Referensi_Image_Movie = findViewById(R.id.isi_referensi_img_movie);

        Intent intent = getIntent();
        Title.setText(intent.getStringExtra("Title_movie"));
        Description.setText(intent.getStringExtra("Description_movie"));
        Date.setText(intent.getStringExtra("Date_movie"));

        Glide.with(MovieDetails.this).load("http://image.tmdb.org/t/p/w185"+intent.getStringExtra("Image_url_movie")).fitCenter().crossFade().
                diskCacheStrategy(DiskCacheStrategy.ALL).into(Referensi_Image_Movie);
    }
}
