package fr.bernesboubaker.moviefindr.ui.favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uwetrottmann.tmdb2.entities.Movie;

import java.util.List;

import fr.bernesboubaker.moviefindr.MovieViewHolder;
import fr.bernesboubaker.moviefindr.R;


public class FavoritesAdapter extends RecyclerView.Adapter<MovieViewHolder> {


    private List<Movie> favoriteMovies;

    public FavoritesAdapter(List<Movie> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_movie, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.updateWithMovie(this.favoriteMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return this.favoriteMovies.size();
    }

}



