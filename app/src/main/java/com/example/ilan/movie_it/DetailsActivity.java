package com.example.ilan.movie_it;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView img;
    Button saveBtn;
    EditText title;
    EditText about;
    int positionFromMain;

    Movie obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.databse_activity);

        final Intent incomingIntent = getIntent();
        final String a = incomingIntent.getStringExtra("movieName");
        final String b = incomingIntent.getStringExtra("ratio");
        final String d = incomingIntent.getStringExtra("release");
        final String e = incomingIntent.getStringExtra("budget");
        final String f = incomingIntent.getStringExtra("about");
        final String c = incomingIntent.getStringExtra("poster");
//        int c = incomingIntent.getIntExtra("poster", 0);

        ((TextView) findViewById(R.id.titletitle)).setText(a);
        ((TextView) findViewById(R.id.aboutbout)).setText(f);
//        ((ImageView) findViewById(R.id.newPoster)).setImageResource(c);
        Picasso.with(this).load(MainActivity.ImageBaseUrl + c).into(((ImageView) findViewById(R.id.newPoster)));
        final EditText url = (EditText) (findViewById(R.id.urlTxt));
        url.setText(c);
        final Button load = (Button) (findViewById(R.id.newurlBtn));

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(url.getText().toString());
            }


        });

        (findViewById(R.id.backBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        (findViewById(R.id.saveBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* String a = ((TextView) findViewById(R.id.titletitle)).getText().toString();
                Intent responseIntent = new Intent();
                responseIntent.putExtra("user", a);
                setResult(RESULT_OK, responseIntent);*/
                obj = new Movie(incomingIntent.getExtras().getInt("id"),
                        ((TextView) findViewById(R.id.titletitle)).getText().toString(),
                        b, d, e,
                        ((TextView) findViewById(R.id.aboutbout)).getText().toString(),
                        url.getText().toString());

                Log.d("errorhere", "   **   " + new DatabaseHandler(DetailsActivity.this).updateMovie(obj));
                finish();

            }
        });

    }

    public void load(String url) {

        img = (ImageView) findViewById(R.id.newPoster);
        Picasso.with(this).load(url).into(img);

    }
}

