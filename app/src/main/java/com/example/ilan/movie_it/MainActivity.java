package com.example.ilan.movie_it;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Find the api and use "Frozen" for example

    public static String ImageBaseUrl = "https://image.tmdb.org/t/p/w500/";
    public static String MoviesUrl = "https://api.themoviedb.org/3/search/movie?api_key=aefbdb7d6699cdfa16e5dc4628adad2a&query=Frozen";

    ListViewAdapter allmoviesAdapter;
    final int newNoteRequestCode = 5;
    int currentPosition = -1;
    List<Movie> allMovies;

    Button addMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allMovies = new DatabaseHandler(this).getAllMovies();
        ListView movieLV = (ListView) findViewById(R.id.movieLV);
        allmoviesAdapter = new ListViewAdapter(this, allMovies);
        movieLV.setAdapter(allmoviesAdapter);
        registerForContextMenu(movieLV);


        movieLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie selectmovies = allMovies.get(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                intent.putExtra("id", selectmovies.getID());
                intent.putExtra("movieName", selectmovies.getMovieName());
                intent.putExtra("ratio", position);
                intent.putExtra("release", position);
                intent.putExtra("budget", position);
                intent.putExtra("about", selectmovies.getAbout());
                intent.putExtra("poster", selectmovies.getImagepath());
                intent.putExtra("pos", position);

                startActivityForResult(intent, newNoteRequestCode);

            }
        });

        addMovie = (Button) findViewById(R.id.addBtn);
        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, addMovie);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.sele, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        /*Toast.makeText(
                                MainActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();*/
                        if (item.getItemId() == R.id.api)
                            startActivity(new Intent(MainActivity.this, AddMovieActivity.class));
                        else
                            startActivity(new Intent(MainActivity.this, AddMovieManualActivity.class));
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Movie> newM = new DatabaseHandler(this).getAllMovies();
        allMovies.clear();
        allMovies.addAll(newM);
        allmoviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            case R.id.edit:
                Movie selectmovies = allMovies.get(info.position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                intent.putExtra("movieName", selectmovies.getMovieName());
                intent.putExtra("about", selectmovies.getAbout());
                intent.putExtra("poster", selectmovies.getResourceID());
                intent.putExtra("pos", info.position);
                startActivityForResult(intent, newNoteRequestCode);
                return true;

            case R.id.delete:
                new DatabaseHandler(this).deleteMovie(allMovies.get(info.position));
                allMovies.remove(info.position);
                allmoviesAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_exit) {
            finish();
        } else {
            new DatabaseHandler(this).deleteAllMovie();
            allMovies.clear();
            allmoviesAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}


// Toast.makeText(MainActivity.this, allMovies.get(i).getMovieName(),Toast.LENGTH_SHORT).show();






