package fr.clerc.myapplication.data;

import android.content.Context;

import com.uwetrottmann.tmdb2.services.MoviesService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.clerc.myapplication.TmdbClient;
import fr.clerc.myapplication.data.local.MoviesLocalDataSource;
import fr.clerc.myapplication.data.remote.MoviesRemoteDataSource;

public class Injection {

    /**
     * Creates an instance of MoviesRemoteDataSource
     */
    public static MoviesRemoteDataSource provideMoviesRemoteDataSource() {
        MoviesService apiService = TmdbClient.getInstance().moviesService();
        return MoviesRemoteDataSource.getInstance(apiService);
    }

/*    Creates an instance of MoviesRemoteDataSource*/

    public static MoviesLocalDataSource provideMoviesLocalDataSource(Context context) {
        return MoviesLocalDataSource.getInstance();
    }

    /**
     * Creates an instance of MovieRepository
     */
    public static MovieDataRepository provideMovieRepository(Context context) {
        MoviesRemoteDataSource remoteDataSource = provideMoviesRemoteDataSource();
        MoviesLocalDataSource localDataSource = provideMoviesLocalDataSource(context);
        //AppExecutors executors = AppExecutors.getInstance();
        return MovieDataRepository.getInstance(remoteDataSource, localDataSource);
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        MovieDataRepository repository = provideMovieRepository(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(repository, executor);
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }
}
