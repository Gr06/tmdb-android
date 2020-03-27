package fr.clerc.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;

import java.util.ArrayList;
import fr.clerc.myapplication.AgencyDetailsActivity;
import fr.clerc.myapplication.R;
import fr.clerc.myapplication.RecyclerViewAdapter;
import fr.clerc.myapplication.TmdbClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.clerc.myapplication.MainActivity.FEATURE_NAME;

public class UpcomingFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerAdapter;

    public static UpcomingFragment newInstance() {
        return (new UpcomingFragment());
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        //int index  = getArguments().getInt(ARG_SECTION_NUMBER, -1);


        recyclerView = view.findViewById(R.id.myRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter= new RecyclerViewAdapter(new ArrayList(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseMovie movie) {
                Intent intent = new Intent(getContext(), AgencyDetailsActivity.class);
                intent.putExtra(FEATURE_NAME, Integer.toString(movie.id));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);

        fetchUpComingMovies();
        //return inflater.inflate(R.layout.fragment_upcoming, container, false);
        return view;
    }

    private void fetchUpComingMovies() {
        MoviesService moviesService = TmdbClient.getInstance().moviesService();
        moviesService.upcoming(1,"FR","FR").enqueue(new Callback<MovieResultsPage>() {

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
