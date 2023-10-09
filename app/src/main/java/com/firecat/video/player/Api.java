package com.firecat.video.player;

/**
 * @author arun
 */
public class Api {
    private static final String BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342";
    private static final String BASR_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780";
    static final String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=%1$s";
    static final String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%1$s/0.jpg";

    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/";

    public static final String TMDB_API_KEY="78cd7c35074dbe3548f20068e58bf0aa"; //69f8d44407d7b73a4103add4c76fccb6

    private Api() {
        // hide implicit public constructor
    }

    public static String getPosterPath(String posterPath) {
        return BASE_POSTER_PATH + posterPath;
    }

    public static String getBackdropPath(String backdropPath) {
        return BASR_BACKDROP_PATH + backdropPath;
    }
}
