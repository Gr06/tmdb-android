package fr.clerc.myapplication.ui.movieDetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.uwetrottmann.tmdb2.entities.Movie;


import fr.clerc.myapplication.R;
import fr.clerc.myapplication.data.Injection;
import fr.clerc.myapplication.data.ViewModelFactory;

import static fr.clerc.myapplication.MainActivity.MOVIE_ID;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieDetailsViewModel movieDetailsViewModel;
    private static int DEFAULT_ID = 1;
    private TextView name;
    private TextView address;
    private TextView type;
    private TextView street;
    private boolean isFavori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        name = findViewById(R.id.movie_id);
        street = findViewById(R.id.feature_street);
        address = findViewById(R.id.feature_address);
        type = findViewById(R.id.feature_type);

        Bundle bundle = getIntent().getExtras();
        int movieId = DEFAULT_ID;
        if(bundle != null) {
            movieId = Integer.parseInt(bundle.getString(MOVIE_ID));
        }

        this.configureViewModel();
        this.movieDetailsViewModel.init(movieId);


        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (movieDetailsViewModel.onFavoriteClicked()) {
                    button.setText(R.string.action_remove_from_favorites);
                } else {
                    button.setText(R.string.action_favorite);
                }
            }
        });

        movieDetailsViewModel.getResult().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                name.setText(Integer.toString(movie.id));
                type.setText(movie.title);
                address.setText("no");
                isFavori = movieDetailsViewModel.isFavorite(movie.id);
                if (isFavori) {
                    button.setText(R.string.action_remove_from_favorites);
                }
            }
        }
        );

        Toolbar toolbar = findViewById(R.id.agencyDetailToolbar);
        setSupportActionBar(toolbar);


        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.movieDetailsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MovieDetailsViewModel.class);
    }


}
