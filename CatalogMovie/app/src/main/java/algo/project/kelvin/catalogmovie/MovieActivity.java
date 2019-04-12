package algo.project.kelvin.catalogmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import algo.project.kelvin.catalogmovie.adapter.MovieAdapter;
import algo.project.kelvin.catalogmovie.db.MovieHelper;
import algo.project.kelvin.catalogmovie.model.MovieModel;

public class MovieActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        recyclerView = findViewById(R.id.recyclerview);

        movieHelper = new MovieHelper(this);
        movieAdapter = new MovieAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);

        movieHelper.open();
        ArrayList<MovieModel> movieModels = movieHelper.getAllData();
        movieHelper.close();

        movieAdapter.setData(movieModels);
    }
}
