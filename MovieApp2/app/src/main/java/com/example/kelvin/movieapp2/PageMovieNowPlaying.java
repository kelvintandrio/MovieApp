package com.example.kelvin.movieapp2;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageMovieNowPlaying extends Fragment {
    private RecyclerView rvCategoryMovieNowPlaying;
    private MovieNowPlayingAdapter movieNowPlayingAdapter;
    private ArrayList<MovieItem> listMovieNowPlaying;
    Context context;

    public PageMovieNowPlaying() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page_movie_now_playing, container, false);
        context = container.getContext();

        rvCategoryMovieNowPlaying = v.findViewById(R.id.rv_category_movie_now_playing);
        rvCategoryMovieNowPlaying.setHasFixedSize(true);
        rvCategoryMovieNowPlaying.setLayoutManager(new LinearLayoutManager(context));

        listMovieNowPlaying = new ArrayList<>();

        loadMovie();
        return v;
    }

    private void loadMovie() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/now_playing?api_key=bdadf5ed74237b6549de22c90ba5d4f7&language=en-US",
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray movieArray = obj.getJSONArray("results");

                            for (int i = 0; i < movieArray.length(); i++) {
                                JSONObject movieObject = movieArray.getJSONObject(i);
                                MovieItem movieItem = new MovieItem(movieObject.getString("title"),
                                        movieObject.getString("overview"),
                                        movieObject.getString("release_date"),
                                        movieObject.getString("poster_path"));
                                listMovieNowPlaying.add(movieItem);
                            }

                            movieNowPlayingAdapter = new MovieNowPlayingAdapter(context, listMovieNowPlaying);
                            rvCategoryMovieNowPlaying.setAdapter(movieNowPlayingAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
