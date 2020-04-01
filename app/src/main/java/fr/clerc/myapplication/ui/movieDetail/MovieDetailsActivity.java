package fr.clerc.myapplication.ui.movieDetail;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.CrewMember;
import com.uwetrottmann.tmdb2.entities.Movie;


import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

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
    private TextView overview;
    private TextView releaseDate;
    private TextView director;
    private boolean isFavori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);
        director = findViewById(R.id.director);
        releaseDate = findViewById(R.id.tv_movie_release_date);
        name = findViewById(R.id.tv_movie_title);
        //street = findViewById(R.id.tv_movie_runtime);
        overview = findViewById(R.id.tv_movie_overview);
        //address = findViewById(R.id.feature_address);
        //type = findViewById(R.id.feature_type);
        ImageView posterImage = findViewById(R.id.iv_movie_poster);
        ImageView backDrop = findViewById(R.id.header_backdrop);

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
                DateFormat dateFormat = new SimpleDateFormat("yyyy");
                String strDate = dateFormat.format(movie.release_date);
                releaseDate.setText(strDate);


                String sDirector ="";
/*                int count = 0;
                for (CrewMember c : movie.credits.crew) {
                    if (c.job == "Director" && count < 1) {
                        sDirector = c.name;
                        count++;
                    }
                }*/

                director.setText(sDirector);
                name.setText(movie.title);
                //type.setText(movie.title);
                //street.setText(movie.title);
                overview.setText(movie.overview);
                Picasso.get().load("https://image.tmdb.org/t/p/w780/"+movie.poster_path).into(posterImage);
                Picasso.get().load("https://image.tmdb.org/t/p/w342/"+movie.backdrop_path).into(backDrop);

                isFavori = movieDetailsViewModel.isFavorite(movie.id);
                if (isFavori) {
                    button.setText(R.string.action_remove_from_favorites);
                }
            }
        }
        );

        Toolbar toolbar = findViewById(R.id.agencyDetailToolbar);
        toolbar.setTitle("Détail film");
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
