package fr.bernesboubaker.moviefindr.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.services.MoviesService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesRemoteDataSource {

    private final MoviesService mMoviesService;

    private static volatile MoviesRemoteDataSource sInstance;


    private MoviesRemoteDataSource(MoviesService moviesService) {
        mMoviesService = moviesService;
    }

    public static MoviesRemoteDataSource getInstance(MoviesService moviesService) {
        if (sInstance == null) {
            sInstance = new MoviesRemoteDataSource(moviesService);
        }
        return sInstance;
    }

    public LiveData<Movie> loadMovie(final long movieId) {
        MoviesService moviesService = TmdbClient.getInstance().moviesService();

        MutableLiveData<Movie> movie = new MutableLiveData<>();
        moviesService.summary((int)movieId, "FR").enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call,
                                   Response<Movie> response) {
                if (response.isSuccessful()){
                    movie.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                movie.setValue(null);
            }
        });
        return movie;
    }
}
