package com.example.ilan.movie_it;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddMovieActivity extends AppCompatActivity {

    private static final String TAG = "errorhere";
    private RequestQueue mRequestQueue;
    ListViewAdapter allmoviesAdapter;
    ArrayList<Movie> allMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        allMovies = new ArrayList<>();

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        ((Button) findViewById(R.id.bt_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pDialog = new ProgressDialog(AddMovieActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                String qury = ((EditText) findViewById(R.id.search_badge)).getText().toString();
                if (qury.length() > 0) {
                    allmoviesAdapter.filter(qury);
                } else
                    allmoviesAdapter.filter("");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.dismiss();
                    }
                }, 3000);
            }
        });

        final ListView movieLV = (ListView) findViewById(R.id.movieLV);
        movieLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectmovies = allMovies.get(position);
                DatabaseHandler mdb = new DatabaseHandler(AddMovieActivity.this);
                Log.d(TAG, "   **    " + mdb.getMoviesCount());
                mdb.addMovie(selectmovies);
                Movie mov = mdb.getAllMovies().get(0);
                Log.d(TAG, "   **    title: " + mov.getMovieName() + "\n" + mov.getImdbRatio() + "\n" + mov.getBudget()
                        + "\n" + mov.getReleaseDate() + "\n" + mov.getAbout() + "\n" + mov.getImagepath());
                finish();
            }
        });

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                MainActivity.MoviesUrl, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject obj = results.getJSONObject(i);
                                Log.d(TAG, obj.getString("title") + "   " + obj.getString("poster_path"));
                                Movie j = new Movie(0, obj.getString("title"), obj.getString("vote_average"), obj.getString("release_date"), obj.getString("popularity"), obj.getString("overview"), MainActivity.ImageBaseUrl + obj.getString("poster_path"));//

                                allMovies.add(j);
                            }
                            allmoviesAdapter = new ListViewAdapter(AddMovieActivity.this, allMovies);
                            movieLV.setAdapter(allmoviesAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });
        mRequestQueue.add(jsonObjReq);
    }
}
