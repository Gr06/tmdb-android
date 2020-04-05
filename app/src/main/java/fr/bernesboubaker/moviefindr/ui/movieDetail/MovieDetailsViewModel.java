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

    //private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    private boolean isFavorite;

    public MovieDetailsViewModel(final MovieDataRepository repository, Executor executor) {
        this.executor = executor;
        this.repository = repository;
    }

    public void init(long movieId) {
        if (result != null) {
            return; // load movie details only once the activity created first time
        }
        result = repository.loadMovie(movieId);

        setMovieIdLiveData(movieId); // trigger loading movie
    }

    public LiveData<Movie> getResult() {
        return result;
    }

/*    public SnackbarMessage getSnackbarMessage() {
        return mSnackbarText;
    }*/

    public boolean isFavorite(int movieId) {
        return repository.isFavorite(movieId);
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    private void setMovieIdLiveData(long movieId) {
        movieIdLiveData.setValue(movieId);
    }

    public void retry(long movieId) {
        setMovieIdLiveData(movieId);
    }

    public boolean onFavoriteClicked() {
        int movieId= result.getValue().id;
        if (!isFavorite(movieId)) {
            System.out.println("pas favori");
            repository.favoriteMovie(movieId);
            isFavorite = true;
            return true;
            //showSnackbarMessage(R.string.movie_added_successfully);
        } else {
            System.out.println("favori");
            repository.unfavoriteMovie(movieId);
            isFavorite = false;
            //showSnackbarMessage(R.string.movie_removed_successfully);
            return false;
        }
    }

/*    private void showSnackbarMessage(Integer message) {
        mSnackbarText.setValue(message);
    }*/


}
