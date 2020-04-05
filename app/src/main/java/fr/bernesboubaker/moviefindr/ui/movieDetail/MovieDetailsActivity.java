package fr.bernesboubaker.moviefindr.ui.movieDetail;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.Videos;
import com.uwetrottmann.tmdb2.services.MoviesService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import fr.bernesboubaker.moviefindr.R;
import fr.bernesboubaker.moviefindr.TmdbClient;
import fr.bernesboubaker.moviefindr.data.Injection;
import fr.bernesboubaker.moviefindr.data.ViewModelFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.bernesboubaker.moviefindr.MainActivity.MOVIE_ID;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieDetailsViewModel movieDetailsViewModel;
    private static int DEFAULT_ID = 1;
    private TextView name;
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
        TextView trailerName = findViewById(R.id.trailer_name);
        ImageView imageTrailer = findViewById(R.id.image_trailer);
        CardView cardTrailer = findViewById(R.id.card_trailer);

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

                MoviesService moviesService = TmdbClient.getInstance().moviesService();

                moviesService.videos(movie.id, "fr-EU").enqueue(new Callback<Videos>() {

                    @Override
                    public void onResponse(Call<Videos> call, Response<Videos> response) {
                        if (response.body() != null && response.body().results != null) {
                            Videos.Video trailer = response.body().results.get(0);

                            trailerName.setText(trailer.name);
                            Picasso.get().load("https://img.youtube.com/vi/" + trailer.key + "/hqdefault.jpg").into(imageTrailer);

                            cardTrailer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent appIntent = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("vnd.youtube:" + trailer.key));
                                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("https://www.youtube.com/watch?v=" + trailer.key));

                                    try {
                                        MovieDetailsActivity.this.startActivity(appIntent);
                                    } catch (ActivityNotFoundException ex) {
                                        MovieDetailsActivity.this.startActivity(webIntent);
                                    }
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<Videos> call, Throwable t) {

                    }
                });

                String sDirector ="";
                director.setText(sDirector);
                name.setText(movie.title);
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
