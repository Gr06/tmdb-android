package fr.bernesboubaker.moviefindr;

import com.uwetrottmann.tmdb2.Tmdb;

public class TmdbClient {
    private static Tmdb tmdb;
    private static final String API_KEY = "1abe855bc465dce9287da07b08a664eb";
    public static Tmdb getInstance() {
        if (tmdb == null) {
            tmdb = new Tmdb(API_KEY);
        }
        return tmdb;
    }
}
