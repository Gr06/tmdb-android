package fr.bernesboubaker.moviefindr.ui;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.uwetrottmann.tmdb2.entities.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.bernesboubaker.moviefindr.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movie_title)
    TextView textView;

    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithMovie(Movie movie){
        this.textView.setText(movie.title);
    }
}