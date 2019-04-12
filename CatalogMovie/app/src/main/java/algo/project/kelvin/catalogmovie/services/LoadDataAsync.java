package algo.project.kelvin.catalogmovie.services;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import algo.project.kelvin.catalogmovie.adapter.MovieAdapter;
import algo.project.kelvin.catalogmovie.db.MovieHelper;
import algo.project.kelvin.catalogmovie.model.MovieModel;
import algo.project.kelvin.catalogmovie.pref.AppPreference;

public class LoadDataAsync extends AsyncTask<Void, Integer, Boolean> {
    private final String TAG = LoadDataAsync.class.getSimpleName();
    private String json = "https://api.themoviedb.org/3/discover/movie?api_key=f7b67d9afdb3c971d4419fa4cb667fbf";
    private MovieHelper movieHelper;
    private MovieAdapter movieAdapter;
    private RecyclerView rvMovie;
    private AppPreference appPreference;
    private WeakReference<LoadDataCallback> weakCallback;
    private WeakReference<Resources> weakResources;
    private Context context;
    double progress;
    double maxprogress = 100;

    LoadDataAsync(MovieHelper movieHelper, AppPreference preference, LoadDataCallback callback, Resources resources, Context context) {
        this.movieHelper = movieHelper;
        this.appPreference = preference;
        this.weakCallback = new WeakReference<>(callback);
        this.weakResources = new WeakReference<>(resources);
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Log.e(TAG, "onPreExecute");
        weakCallback.get().onPreLoad();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        weakCallback.get().onProgressUpdate(values[0]);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean firstRun = appPreference.getFirstRun();
        if (firstRun) {
            ArrayList<MovieModel> movieModels = preLoadRaw();
            movieHelper.open();

            progress = 30;
            publishProgress((int) progress);
            Double progressMaxInsert = 80.0;
            Double progressDiff = (progressMaxInsert - progress) / movieModels.size();

            boolean isInsertSuccess;
            try {
                for (MovieModel model : movieModels) {
                    movieHelper.insert(model);
                    progress += progressDiff;
                    publishProgress((int) progress);
                    System.out.println("ZZ");
                }
                isInsertSuccess = true;
                appPreference.setFirstRun(false);
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: Exception");
                isInsertSuccess = false;
            }
            movieHelper.close();

            publishProgress((int) maxprogress);

            return isInsertSuccess;
        } else {
            try {
                synchronized (this) {
                    this.wait(2000);
                    publishProgress(50);

                    this.wait(2000);
                    publishProgress((int) maxprogress);

                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            weakCallback.get().onLoadSuccess();
        } else {
            weakCallback.get().onLoadFailed();
        }
    }

    private ArrayList<MovieModel> preLoadRaw() {
        final ArrayList<MovieModel> movieModels = new ArrayList<>();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, json,
                    new Response.Listener<String>() {
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONArray movieArray = obj.getJSONArray("results");

                                for (int i = 0; i < 10; i++) {
                                    JSONObject movieObject = movieArray.getJSONObject(i);
                                    MovieModel movieItem = new MovieModel(movieObject.getString("original_title"),
                                            movieObject.getString("release_date"));
                                    movieModels.add(movieItem);
                                }

//                                movieAdapter = new MovieAdapter(context, movieModels);
//                                rvMovie.setAdapter(movieAdapter);

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

//            Resources res = weakResources.get();
//            InputStream raw_dict = res.openRawResource(R.raw.data_movie);
//            reader = new BufferedReader(new InputStreamReader(raw_dict));
//            do {
//                line = reader.readLine();
//                String[] splitstr = line.split("\t");
//
//                MovieModel movieModel;
//
//                movieModel = new MovieModel(splitstr[0], splitstr[1]);
//                movieModels.add(movieModel);
//            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieModels;
    }
}