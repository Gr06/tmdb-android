package fr.bernesboubaker.moviefindr.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.services.MoviesService;

import fr.bernesboubaker.moviefindr.TmdbClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Yassin Ajdi.
 */
public class MoviesRemoteDataSource {

    private static final int PAGE_SIZE = 20;
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



    /**
     * Load movies for certain filter.
     */
/*    public RepoMoviesResult loadMoviesFilteredBy(MoviesFilterType sortBy) {
        MovieDataSourceFactory sourceFactory =
                new MovieDataSourceFactory(mMovieService, mExecutors.networkIO(), sortBy);

        // paging configuration
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        // Get the paged list
        LiveData<PagedList<Movie>> moviesPagedList = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(mExecutors.networkIO())
                .build();

        LiveData<Resource> networkState = Transformations.switchMap(sourceFactory.sourceLiveData, new Function<MoviePageKeyedDataSource, LiveData<Resource>>() {
            @Override
            public LiveData<Resource> apply(MoviePageKeyedDataSource input) {
                return input.networkState;
            }
        });

        // Get pagedList and network errors exposed to the viewmodel
        return new RepoMoviesResult(
                moviesPagedList,
                networkState,
                sourceFactory.sourceLiveData
        );
    }*/
}
