package com.example.kelvin.movieapp2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class MovieNowPlayingAdapter extends RecyclerView.Adapter <MovieNowPlayingAdapter.MovieNowPlayingHolder> {
    private static final String IMAGE_MOVIE_NOW_PLAYING = "Image_url_movie";
    private static final String TITLE_MOVIE_NOW_PLAYING = "Title_movie";
    private static final String DESCRIPTION_MOVIE_NOW_PLAYING = "Description_movie";
    private static final String DATE_MOVIE_NOW_PLAYING = "Date_movie";

    private Context mcontext;
    private ArrayList<MovieItem> listMovieNowPlaying;

    MovieNowPlayingAdapter(Context context, ArrayList<MovieItem> mlistMovieNowPlaying) {
        this.mcontext = context;
        this.listMovieNowPlaying = mlistMovieNowPlaying;
    }

    private ArrayList<MovieItem> getListMovieNowPlaying() {
        return listMovieNowPlaying;
    }

    @NonNull
    @Override
    public MovieNowPlayingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieNowPlayingHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieNowPlayingHolder holder, int position) {
        MovieItem movieItem = listMovieNowPlaying.get(position);
        holder.Title_MovieNowPlaying.setText(movieItem.getJudul());
        holder.Date_MovieNowPlaying.setText(movieItem.getTanggal());
        Glide.with(mcontext).load("http://image.tmdb.org/t/p/w185"+movieItem.getGambar()).fitCenter().into(holder.Img_MovieNowPlaying);

        holder.Details_Movie_Now_Playing.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            public void onItemClicked(View view, int position) {
                String TitleMovieNowPlaying = getListMovieNowPlaying().get(position).getJudul();
                String DescriptionMovieNowPlaying = getListMovieNowPlaying().get(position).getDeskripsi();
                String DateMovieNowPlaying = getListMovieNowPlaying().get(position).getTanggal();
                String ImageMovieNowPlaying = getListMovieNowPlaying().get(position).getGambar();

                Intent moveTo = new Intent(mcontext, MovieDetails.class);
                moveTo.putExtra(IMAGE_MOVIE_NOW_PLAYING, ImageMovieNowPlaying);
                moveTo.putExtra(TITLE_MOVIE_NOW_PLAYING, TitleMovieNowPlaying);
                moveTo.putExtra(DESCRIPTION_MOVIE_NOW_PLAYING, DescriptionMovieNowPlaying);
                moveTo.putExtra(DATE_MOVIE_NOW_PLAYING, DateMovieNowPlaying);

                mcontext.startActivity(moveTo);
                Toast.makeText(mcontext, "Anda pilih "+TitleMovieNowPlaying, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovieNowPlaying.size();
    }


    class MovieNowPlayingHolder extends RecyclerView.ViewHolder {
        ImageView Img_MovieNowPlaying;
        TextView Title_MovieNowPlaying, Description_MovieNowPlaying, Date_MovieNowPlaying;
        Button Details_Movie_Now_Playing;

        MovieNowPlayingHolder(View itemView) {
            super(itemView);
            Img_MovieNowPlaying = itemView.findViewById(R.id.img_movie);
            Title_MovieNowPlaying = itemView.findViewById(R.id.title_movie);
            Description_MovieNowPlaying = itemView.findViewById(R.id.description_movie);
            Date_MovieNowPlaying = itemView.findViewById(R.id.date_movie);
            Details_Movie_Now_Playing = itemView.findViewById(R.id.btn_detail_movie);
        }
    }
}
