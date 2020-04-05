package fr.bernesboubaker.moviefindr.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Movie;

import java.util.ArrayList;
import java.util.List;

import fr.bernesboubaker.moviefindr.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<BaseMovie> movies;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, BaseMovie movie);
    }

    public RecyclerViewAdapter(List<BaseMovie> movies, OnItemClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    public void addMovies(List<BaseMovie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void clearMovies() {
        this.movies = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BaseMovie movie = movies.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w185/"+movie.poster_path).into(holder.image);
        holder.title.setText(movie.title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, movie);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View itemView;
        final TextView title;
        final ImageView image;

        ViewHolder(View view) {
            super(view);
            itemView = view;
            title = view.findViewById(R.id.movie_title);
            image = view.findViewById(R.id.movie_poster);
        }
    }

}
