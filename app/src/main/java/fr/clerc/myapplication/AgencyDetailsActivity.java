package fr.clerc.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.services.MoviesService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.clerc.myapplication.MainActivity.FEATURE_NAME;

public class AgencyDetailsActivity extends AppCompatActivity {

    private TextView name;
    private TextView address;
    private TextView type;
    private TextView street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        name = findViewById(R.id.feature_name);
        street = findViewById(R.id.feature_street);
        address = findViewById(R.id.feature_address);
        type = findViewById(R.id.feature_type);

        Toolbar toolbar = findViewById(R.id.agencyDetailToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Integer moveId = Integer.parseInt(bundle.getString(FEATURE_NAME));
            fetchMovieData(moveId);
        }
    }

    private void fetchMovieData(Integer movieId) {
        MoviesService moviesService = TmdbClient.getInstance().moviesService();
        moviesService.summary(movieId, "FR").enqueue(new Callback<Movie>() {

            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //Manage data
                    Movie movie = response.body();
                    displayData(movie);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.app_error), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }

        });
    }

    private void displayData(Movie movie) {
        name.setText(Integer.toString(movie.id));
        type.setText(movie.title);
        address.setText("no");
    }
}
