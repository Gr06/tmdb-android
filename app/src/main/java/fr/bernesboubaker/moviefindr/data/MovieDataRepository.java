package fr.bernesboubaker.moviefindr.data;

import androidx.lifecycle.LiveData;

import com.uwetrottmann.tmdb2.entities.Movie;

import java.util.List;

import fr.bernesboubaker.moviefindr.data.local.MoviesLocalDataSource;
import fr.bernesboubaker.moviefindr.data.remote.MoviesRemoteDataSource;

public class MovieDataRepository {
    private static volatile MovieDataRepository sInstance;

    private final MoviesRemoteDataSource mRemoteDataSource;
    private final MoviesLocalDataSource mLocalDataSource;


    private MovieDataRepository(MoviesRemoteDataSource remoteDataSource, MoviesLocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static MovieDataRepository getInstance(MoviesRemoteDataSource remoteDataSource, MoviesLocalDataSource localDataSource) {
        if (sInstance == null) {
            synchronized (MovieDataRepository.class) {
                if (sInstance == null) {
                    sInstance = new MovieDataRepository(remoteDataSource, localDataSource);
                }
            }
        }
        return sInstance;
    }

    public LiveData<Movie> loadMovie(final long movieId) {
        return mRemoteDataSource.loadMovie(movieId);
    }

/*    public LiveData<MovieResultsPage> loadUpComingMovies() {
        return mRemoteDataSource.loadUpComingMovies();
    }*/

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return null;
        //return mLocalDataSource.getAllFavoriteMoviesIds();
    }

    public boolean isFavorite(final int movieId) {
        return mLocalDataSource.isFavorite(movieId);
    }

    public void favoriteMovie(final int movieId) {
        mLocalDataSource.favoriteMovie(movieId);
    }


    public void unfavoriteMovie(final int movieId) {
        mLocalDataSource.unfavoriteMovie(movieId);
    }

}
