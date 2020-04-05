package fr.bernesboubaker.moviefindr.data;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executor;

import fr.bernesboubaker.moviefindr.ui.movieDetail.MovieDetailsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final MovieDataRepository mMovieDataSource;
    private final Executor executor;

    public ViewModelFactory(MovieDataRepository movieDataSource, Executor executor) {
        this.mMovieDataSource = movieDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel.class)) {
            return (T) new MovieDetailsViewModel(mMovieDataSource, executor);

        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
