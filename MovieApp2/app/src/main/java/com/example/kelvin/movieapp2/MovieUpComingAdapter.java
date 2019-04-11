package com.example.kelvin.movieapp2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieUpComingAdapter extends RecyclerView.Adapter <MovieUpComingAdapter.MovieUpComingHolder> {
    private static final String IMAGE_MOVIE_UP_COMING = "Image_url_movie";
    private static final String TITLE_MOVIE_UP_COMING = "Title_movie";
    private static final String DESCRIPTION_MOVIE_UP_COMING = "Description_movie";
    private static final String DATE_MOVIE_UP_COMING = "Date_movie";

    private ArrayList<MovieItem> listMovieUpComing;
    private Context mcontext;

    MovieUpComingAdapter(Context context, ArrayList<MovieItem> mlistMovieUpComing) {
        this.mcontext = context;
        this.listMovieUpComing = mlistMovieUpComing;
    }

    private ArrayList<MovieItem> getListMovieUpComing() {
        return listMovieUpComing;
    }

    @Override
    public MovieUpComingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieUpComingHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieUpComingHolder holder, int position) {
        MovieItem movieItem = listMovieUpComing.get(position);
        holder.Title_MovieUpComing.setText(movieItem.getJudul());
        holder.Date_MovieUpComing.setText(movieItem.getTanggal());
        Glide.with(mcontext).load("http://image.tmdb.org/t/p/w185"+movieItem.getGambar()).fitCenter().into(holder.Img_MovieUpComing);

        holder.Details_Movie_Up_Coming.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            public void onItemClicked(View view, int position) {
                String TitleMovieUpComing = getListMovieUpComing().get(position).getJudul();
                String DescriptionMovieUpComing = getListMovieUpComing().get(position).getDeskripsi();
                String DateMovieUpComing = getListMovieUpComing().get(position).getTanggal();
                String ImageMovieUpComing = getListMovieUpComing().get(position).getGambar();

                Intent moveTo = new Intent(mcontext, MovieDetails.class);
                moveTo.putExtra(IMAGE_MOVIE_UP_COMING, ImageMovieUpComing);
                moveTo.putExtra(TITLE_MOVIE_UP_COMING, TitleMovieUpComing);
                moveTo.putExtra(DESCRIPTION_MOVIE_UP_COMING, DescriptionMovieUpComing);
                moveTo.putExtra(DATE_MOVIE_UP_COMING, DateMovieUpComing);

                mcontext.startActivity(moveTo);
                Toast.makeText(mcontext, "Anda pilih "+TitleMovieUpComing, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovieUpComing.size();
    }


    class MovieUpComingHolder extends RecyclerView.ViewHolder {
        ImageView Img_MovieUpComing;
        TextView Title_MovieUpComing, Description_MovieUpComing, Date_MovieUpComing;
        Button Details_Movie_Up_Coming;

        MovieUpComingHolder(View itemView) {
            super(itemView);
            Img_MovieUpComing = itemView.findViewById(R.id.img_movie);
            Title_MovieUpComing = itemView.findViewById(R.id.title_movie);
            Description_MovieUpComing = itemView.findViewById(R.id.description_movie);
            Date_MovieUpComing = itemView.findViewById(R.id.date_movie);
            Details_Movie_Up_Coming = itemView.findViewById(R.id.btn_detail_movie);
        }
    }
}
