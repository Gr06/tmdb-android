package fr.clerc.myapplication.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executor;

import fr.clerc.myapplication.ui.favorites.FavoritesViewModel;
import fr.clerc.myapplication.ui.movieDetail.MovieDetailsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final MovieDataRepository mMovieDataSource;
    private final Executor executor;

    public ViewModelFactory(MovieDataRepository movieDataSource, Executor executor) {
        this.mMovieDataSource = movieDataSource;;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel.class)) {
            return (T) new MovieDetailsViewModel(mMovieDataSource, executor);
        } else if (modelClass.isAssignableFrom(FavoritesViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoritesViewModel(mMovieDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
