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
import android.widget.Button;
import android.widget.EditText;

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
public class PageSearchMovie extends Fragment implements View.OnClickListener {

    private RecyclerView rvCategoryMovieSearch;
    private MovieSearchAdapter movieSearchAdapter;
    private ArrayList<MovieItem> listMovieSearch;
    private EditText KeywordMovie;
    Context context;

    public PageSearchMovie() {
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
        View v = inflater.inflate(R.layout.fragment_page_search_movie, container, false);
        context = container.getContext();

        rvCategoryMovieSearch = v.findViewById(R.id.rv_category_search_movie);
        rvCategoryMovieSearch.setHasFixedSize(true);
        rvCategoryMovieSearch.setLayoutManager(new LinearLayoutManager(context));

        listMovieSearch = new ArrayList<>();

        KeywordMovie = v.findViewById(R.id.keyword_search_movie);
        Button ToSearch = v.findViewById(R.id.process_search_movie);
        ToSearch.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        String keyword = KeywordMovie.getText().toString();
        String URL_JSON = "https://api.themoviedb.org/3/search/movie?api_key=bdadf5ed74237b6549de22c90ba5d4f7&language=en-" +
                "US&query="+keyword;
        loadMovie(URL_JSON);
    }

    private void loadMovie(String json) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, json,
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
                                listMovieSearch.add(movieItem);
                            }

                            movieSearchAdapter = new MovieSearchAdapter(context, listMovieSearch);
                            rvCategoryMovieSearch.setAdapter(movieSearchAdapter);

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
