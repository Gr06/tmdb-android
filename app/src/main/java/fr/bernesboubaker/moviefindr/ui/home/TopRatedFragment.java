package fr.bernesboubaker.moviefindr.ui.home;

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

import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;

import java.util.ArrayList;

import fr.bernesboubaker.moviefindr.R;
import fr.bernesboubaker.moviefindr.RecyclerViewAdapter;
import fr.bernesboubaker.moviefindr.TmdbClient;
import fr.bernesboubaker.moviefindr.ui.movieDetail.MovieDetailsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.bernesboubaker.moviefindr.MainActivity.MOVIE_ID;

public class TopRatedFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerAdapter;

    public static TopRatedFragment newInstance() {
        return (new TopRatedFragment());
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        //int index  = getArguments().getInt(ARG_SECTION_NUMBER, -1);


        recyclerView = view.findViewById(R.id.myRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter= new RecyclerViewAdapter(new ArrayList(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseMovie movie) {
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra(MOVIE_ID, Integer.toString(movie.id));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

        final int[] loadedPageId = {1};
        fetchTopRatedMovies(loadedPageId[0]);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    loadedPageId[0]++;
                    fetchTopRatedMovies(loadedPageId[0]);
                }
            }
        });
        return view;
    }

    private void fetchTopRatedMovies(int loadedPageId) {
        MoviesService moviesService = TmdbClient.getInstance().moviesService();
        moviesService.topRated(loadedPageId, "fr-EU", null).enqueue(new Callback<MovieResultsPage>() {

            @Override
            public void onResponse(Call<MovieResultsPage> call, Response<MovieResultsPage> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    MovieResultsPage movies = response.body();
                    recyclerAdapter.addMovies(movies.results);
                } else {
                    Toast.makeText(getContext(), getString(R.string.app_error), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResultsPage> call, Throwable t) {
                //Manage errors
            }
        });
    }
}
