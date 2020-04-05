package fr.bernesboubaker.moviefindr.ui.movieDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.uwetrottmann.tmdb2.entities.Movie;

import java.util.concurrent.Executor;

import fr.bernesboubaker.moviefindr.data.MovieDataRepository;


public class MovieDetailsViewModel extends ViewModel {

    private final MovieDataRepository repository;
    private final Executor executor;

    private LiveData<Movie> result;

    private MutableLiveData<Long> movieIdLiveData = new MutableLiveData<>();

    private boolean isFavorite;

    public MovieDetailsViewModel(final MovieDataRepository repository, Executor executor) {
        this.executor = executor;
        this.repository = repository;
    }

    public void init(long movieId) {
        if (result != null) {
            return;
        }
        result = repository.loadMovie(movieId);
        setMovieIdLiveData(movieId);
    }

    public LiveData<Movie> getResult() {
        return result;
    }

    public boolean isFavorite(int movieId) {
        return repository.isFavorite(movieId);
    }

    private void setMovieIdLiveData(long movieId) {
        movieIdLiveData.setValue(movieId);
    }

    public boolean onFavoriteClicked() {
        int movieId= result.getValue().id;
        if (!isFavorite(movieId)) {
            repository.favoriteMovie(movieId);
            isFavorite = true;
            return true;
        }
        else {
            repository.unfavoriteMovie(movieId);
            isFavorite = false;
            return false;
        }
    }

}
