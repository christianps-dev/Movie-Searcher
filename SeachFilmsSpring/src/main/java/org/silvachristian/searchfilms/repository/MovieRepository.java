package org.silvachristian.searchfilms.repository;

import org.silvachristian.searchfilms.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    MovieEntity findFilmDetailsByFilmTitle(String movieTitle);

    boolean existsByFilmTitle(String movieTitle);

    @Query("SELECT m.id FROM movies m WHERE m.filmTitle = :movieTitle")
    Long findMovieByTitle(String movieTitle);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
            "FROM movies m JOIN favorites f ON m.id = f.movieId WHERE f.userId = :userId AND m.filmTitle = :movieTitle")
    boolean checkIfUserAlreadySearched(Long userId, String movieTitle);

}
