package fr.bernesboubaker.moviefindr.ui.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uwetrottmann.tmdb2.entities.Movie;

import java.util.List;

import fr.bernesboubaker.moviefindr.data.MovieDataRepository;

public class FavoritesViewModel extends ViewModel {

    private LiveData<List<Movie>> favoriteListLiveData;

    public FavoritesViewModel(MovieDataRepository repository) {
        favoriteListLiveData = repository.getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteListLiveData() {
        return favoriteListLiveData;
    }
}

