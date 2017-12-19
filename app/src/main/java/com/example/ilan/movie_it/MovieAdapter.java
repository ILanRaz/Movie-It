package com.example.ilan.movie_it;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MovieAdapter extends BaseAdapter {

     List<Movie> myMovies;
     int layout;
     Context context;


    public MovieAdapter(@NonNull Context context, List<Movie> myMovies) {
        this.context = context;
        this.layout = layout;
        this.myMovies = myMovies;
    }


    @Override
    public int getCount() {
        return myMovies.size();
    }

    @Override
    public Object getItem(int i) {
        return myMovies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class MyviewHolder {

        ImageView moviePoster;
        TextView movieName;
        TextView budget;
        TextView releaseDate;
        TextView imdbRatio;
        TextView body;
    }

    @Override
    public View getView(int i,  View view, ViewGroup parent) {
        MyviewHolder holder;

        if (view==null) {

            holder = new MyviewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.details_activity, null);
            holder.budget = (TextView) view.findViewById(R.id.budget);
            holder.imdbRatio = (TextView) view.findViewById(R.id.imdbRatio);
            holder.movieName = (TextView) view.findViewById(R.id.movieName);
            holder.moviePoster = (ImageView) view.findViewById(R.id.moviePoster);
            holder.releaseDate = (TextView) view.findViewById(R.id.releaseDate);
            holder.body = (EditText) view.findViewById(R.id.aboutbout);
            view.setTag(holder);
        }
        else

        {
          holder = (MyviewHolder) view.getTag();
        }

        holder.moviePoster.setImageResource(myMovies.get(i).getResourceID());
        holder.budget.setText(myMovies.get(i).getBudget());
        holder.imdbRatio.setText(myMovies.get(i).getImdbRatio());
        holder.movieName.setText(myMovies.get(i).getMovieName());
        holder.releaseDate.setText(myMovies.get(i).getReleaseDate());


        return view;
    }
}








