package fr.clerc.myapplication.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.SearchService;

import java.util.ArrayList;

import fr.clerc.myapplication.R;
import fr.clerc.myapplication.RecyclerViewAdapter;
import fr.clerc.myapplication.TmdbClient;
import fr.clerc.myapplication.ui.movieDetail.MovieDetailsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.clerc.myapplication.MainActivity.MOVIE_ID;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerAdapter;
    private SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.myRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        recyclerAdapter = new RecyclerViewAdapter(new ArrayList(), (view1, movie) -> {
            Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
            intent.putExtra(MOVIE_ID, Integer.toString(movie.id));
            startActivity(intent);
        });
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


        searchView = view.findViewById(R.id.search_bar);
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovieByName(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    private void searchMovieByName(String movieName) {
        SearchService searchService = TmdbClient.getInstance().searchService();
        searchService.movie(movieName, 1, "FR", "FR", false, null, null).enqueue(new Callback<MovieResultsPage>() {
            @Override
            public void onResponse(Call<MovieResultsPage> call, Response<MovieResultsPage> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MovieResultsPage movies = response.body();
                    recyclerAdapter.clearMovies();
                    recyclerAdapter.addMovies(movies.results);
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_movie_found), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResultsPage> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.app_error), Toast.LENGTH_LONG).show();
            }
        });
    }
}