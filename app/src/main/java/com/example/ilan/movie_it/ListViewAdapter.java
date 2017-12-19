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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    List<Movie> myMovies;
    List<Movie> searchList;
    int layout;
    Context context;


    public ListViewAdapter(@NonNull Context context, List<Movie> myMovies) {
        this.context = context;
        this.layout = layout;
        this.myMovies = myMovies;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(myMovies);
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
    public View getView(int i, View view, ViewGroup parent) {
        ListViewAdapter.MyviewHolder holder;

        if (view == null) {
            holder = new ListViewAdapter.MyviewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.details_activity, null);
            holder.budget = (TextView) view.findViewById(R.id.budget);
            holder.imdbRatio = (TextView) view.findViewById(R.id.imdbRatio);
            holder.movieName = (TextView) view.findViewById(R.id.movieName);
            holder.moviePoster = (ImageView) view.findViewById(R.id.moviePoster);
            holder.releaseDate = (TextView) view.findViewById(R.id.releaseDate);
            holder.body = (EditText) view.findViewById(R.id.aboutbout);
            view.setTag(holder);
        } else

        {
            holder = (ListViewAdapter.MyviewHolder) view.getTag();
        }

//        holder.moviePoster.setImageResource(myMovies.get(i).getResourceID());
        if (!myMovies.get(i).getImagepath().isEmpty())
            Picasso.with(context).load(myMovies.get(i).getImagepath()).into(holder.moviePoster);
        holder.budget.setText("Budget:" + myMovies.get(i).getBudget());
        holder.imdbRatio.setText("Ratio: " + myMovies.get(i).getImdbRatio());
        holder.movieName.setText("Movie Name: " + myMovies.get(i).getMovieName());
        holder.releaseDate.setText("Release Date: " + myMovies.get(i).getReleaseDate());


        return view;
    }

    // Filter method
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myMovies.clear();
        if (charText.length() == 0) {
            myMovies.addAll(searchList);
        } else {
            for (Movie s : searchList) {
                if (s.getMovieName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    myMovies.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }
}