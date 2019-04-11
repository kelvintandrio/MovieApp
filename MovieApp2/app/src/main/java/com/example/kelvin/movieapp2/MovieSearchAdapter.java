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

public class MovieSearchAdapter extends RecyclerView.Adapter <MovieSearchAdapter.MovieSearchHolder> {
    private static final String IMAGE_MOVIE_SEARCH = "Image_url_movie";
    private static final String TITLE_MOVIE_SEARCH = "Title_movie";
    private static final String DESCRIPTION_MOVIE_SEARCH = "Description_movie";
    private static final String DATE_MOVIE_SEARCH = "Date_movie";

    private Context mcontext;
    private ArrayList<MovieItem> listMovieSearch;

    MovieSearchAdapter(Context context, ArrayList<MovieItem> mlistMovieSearch) {
        this.mcontext = context;
        this.listMovieSearch = mlistMovieSearch;
    }

    private ArrayList<MovieItem> getListMovieSearch() {
        return listMovieSearch;
    }

    @Override
    public MovieSearchAdapter.MovieSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieSearchAdapter.MovieSearchHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieSearchAdapter.MovieSearchHolder holder, int position) {
        MovieItem movieItem = listMovieSearch.get(position);
        holder.Title_MovieSearch.setText(movieItem.getJudul());
        holder.Date_MovieSearch.setText(movieItem.getTanggal());
        Glide.with(mcontext).load("http://image.tmdb.org/t/p/w185"+movieItem.getGambar()).fitCenter().into(holder.Img_MovieSearch);

        holder.Details_SearchMovie.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            public void onItemClicked(View view, int position) {
                String TitleMovieSearch = getListMovieSearch().get(position).getJudul();
                String DescriptionMovieSearch = getListMovieSearch().get(position).getDeskripsi();
                String DateMovieSearch = getListMovieSearch().get(position).getTanggal();
                String ImageMovieSearch = getListMovieSearch().get(position).getGambar();

                Intent moveTo = new Intent(mcontext, MovieDetails.class);
                moveTo.putExtra(IMAGE_MOVIE_SEARCH, ImageMovieSearch);
                moveTo.putExtra(TITLE_MOVIE_SEARCH, TitleMovieSearch);
                moveTo.putExtra(DESCRIPTION_MOVIE_SEARCH, DescriptionMovieSearch);
                moveTo.putExtra(DATE_MOVIE_SEARCH, DateMovieSearch);

                mcontext.startActivity(moveTo);
                Toast.makeText(mcontext, "Anda pilih "+TitleMovieSearch, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovieSearch.size();
    }


    class MovieSearchHolder extends RecyclerView.ViewHolder {
        ImageView Img_MovieSearch;
        TextView Title_MovieSearch, Description_MovieSearch, Date_MovieSearch;
        Button Details_SearchMovie;

        MovieSearchHolder(View itemView) {
            super(itemView);
            Img_MovieSearch = itemView.findViewById(R.id.img_movie);
            Title_MovieSearch = itemView.findViewById(R.id.title_movie);
            Description_MovieSearch = itemView.findViewById(R.id.description_movie);
            Date_MovieSearch = itemView.findViewById(R.id.date_movie);
            Details_SearchMovie = itemView.findViewById(R.id.btn_detail_movie);
        }
    }
}