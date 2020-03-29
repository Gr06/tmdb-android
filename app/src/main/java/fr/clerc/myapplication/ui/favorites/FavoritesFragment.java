package fr.clerc.myapplication.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;

import java.util.ArrayList;

import fr.clerc.myapplication.data.local.MoviesLocalDataSource;
import fr.clerc.myapplication.ui.home.SectionsPagerAdapter;
import fr.clerc.myapplication.ui.movieDetail.MovieDetailsActivity;
import fr.clerc.myapplication.R;
import fr.clerc.myapplication.RecyclerViewAdapter;
import fr.clerc.myapplication.TmdbClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.clerc.myapplication.MainActivity.MOVIE_ID;

public class FavoritesFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerAdapter;

    public static FavoritesFragment newInstance() {
        return (new FavoritesFragment());
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        recyclerView = view.findViewById(R.id.myRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter= new RecyclerViewAdapter(new ArrayList(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseMovie movie) {
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra(MOVIE_ID, movie.id);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

        fetchPopularMovies();
        return view;
    }

    private void fetchPopularMovies() {
        ArrayList<Integer> favoritesIds = new ArrayList<Integer>();
        favoritesIds = MoviesLocalDataSource.getInstance().getAllFavoriteMoviesIds();

        MoviesService moviesService = TmdbClient.getInstance().moviesService();
        for (int movieId : favoritesIds) {
            moviesService.summary(movieId, "FR").enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    Movie m = response.body();
                    recyclerAdapter.addMovie(m);
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                }
            });
        }

    }
}
