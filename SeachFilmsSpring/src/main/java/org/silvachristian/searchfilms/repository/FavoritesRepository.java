package org.silvachristian.searchfilms.repository;

import org.silvachristian.searchfilms.entity.FavoriteEntity;
import org.silvachristian.searchfilms.entity.FavoritesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<FavoriteEntity, Long> {

    @Query("SELECT m.filmTitle AS filmTitle, m.posterURL AS posterURL, m.filmGenre AS filmGenre " +
            "FROM movies m JOIN favorites f ON f.movieId = m.id JOIN users u on u.id = f.userId " +
            "WHERE m.filmGenre LIKE %:genre% ")
    List<FavoritesInfo> findByGenre(String genre);

    @Query("SELECT m.filmTitle AS filmTitle, m.posterURL AS posterURL, m.filmGenre AS filmGenre " +
            "FROM movies m JOIN favorites f ON f.movieId = m.id WHERE f.userId = :userId")
    List<FavoritesInfo> findAllFavorites(Long userId);

}
