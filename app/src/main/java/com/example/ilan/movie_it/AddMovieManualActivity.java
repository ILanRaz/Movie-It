package com.example.ilan.movie_it;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddMovieManualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_manual);

        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((EditText) findViewById(R.id.title)).getText().toString();
                String ratio = ((EditText) findViewById(R.id.ratio)).getText().toString();
                String release = ((EditText) findViewById(R.id.budget)).getText().toString();
                String budget = ((EditText) findViewById(R.id.release)).getText().toString();
                String about = ((EditText) findViewById(R.id.about)).getText().toString();
                String imgPath = ((EditText) findViewById(R.id.img)).getText().toString();

                new DatabaseHandler(AddMovieManualActivity.this).addMovie(new Movie(0, title, ratio, release, budget, about, imgPath));
                finish();
            }
        });
    }
}
