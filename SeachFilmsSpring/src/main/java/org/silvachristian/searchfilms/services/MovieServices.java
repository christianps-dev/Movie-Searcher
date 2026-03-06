package org.silvachristian.searchfilms.services;

import org.silvachristian.searchfilms.entity.FavoriteEntity;
import org.silvachristian.searchfilms.entity.FavoritesInfo;
import org.silvachristian.searchfilms.repository.FavoritesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.silvachristian.searchfilms.entity.MovieEntity;
import org.silvachristian.searchfilms.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class MovieServices {

    private final RestClient restClient;
    private final MovieRepository movieRepository;
    private final String apiKey;
    private final FavoritesRepository favoritesRepository;

    MovieServices(RestClient.Builder builder, MovieRepository movieRepository,
                  @Value("${omdb-api-key}") String apiKeyProperties, FavoritesRepository favoritesRepository) {
        this.restClient = builder.baseUrl("http://www.omdbapi.com/").build();
        this.movieRepository = movieRepository;
        this.apiKey = apiKeyProperties;
        this.favoritesRepository = favoritesRepository;
    }

    public boolean checkIfAlreadySearched(String movieTitle) {
        return movieRepository.existsByFilmTitle(movieTitle);
    }

    public MovieEntity findByTitle(String movieTitle, Long userId) {

        movieTitle = stringToCapital(movieTitle);
        FavoriteEntity favoriteMovie = new FavoriteEntity();

        if (checkIfAlreadySearched(movieTitle)) {
            MovieEntity movieReturned = movieRepository.findFilmDetailsByFilmTitle(movieTitle);
            if (!movieRepository.checkIfUserAlreadySearched(userId, movieTitle)) {

                favoriteMovie.setMovieId(movieReturned.getId());
                favoriteMovie.setUserId(userId);
                favoritesRepository.save(favoriteMovie);
            }
            return movieReturned;
        }

        String finalMovieTitle = movieTitle;
        MovieEntity newMovie = restClient.get().uri(uriBuilder -> uriBuilder
                .queryParam("t", finalMovieTitle)
                .queryParam("apikey", apiKey)
                .build()).retrieve().body(MovieEntity.class);

        assert newMovie != null;
        movieRepository.save(newMovie);

        favoriteMovie.setMovieId(movieRepository.findMovieByTitle(newMovie.getFilmTitle()));
        favoriteMovie.setUserId(userId);
        favoritesRepository.save(favoriteMovie);

        return newMovie;
    }

    public String stringToCapital(String movieTitle) {

        String[] movieWords = movieTitle.split(" ");
        StringBuilder finalMovieTitle = new StringBuilder();
        for (String movieWord : movieWords) {
            movieWord = movieWord.substring(0, 1).toUpperCase() + movieWord.substring(1).toLowerCase();
            finalMovieTitle.append(" ").append(movieWord);
        }

        return finalMovieTitle.toString().trim();
    }

    public List<FavoritesInfo> showFavorites(String movieGenre, Long userId) {

        if (movieGenre.isEmpty() || movieGenre.equals("all")) {
            return favoritesRepository.findAllFavorites(userId);
        }

        return favoritesRepository.findByGenre(stringToCapital(movieGenre));

    }
}



