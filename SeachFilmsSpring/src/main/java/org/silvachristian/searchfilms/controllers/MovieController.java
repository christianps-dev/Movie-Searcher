package org.silvachristian.searchfilms.controllers;

import org.silvachristian.searchfilms.entity.FavoritesInfo;
import org.silvachristian.searchfilms.entity.MovieEntity;
import org.silvachristian.searchfilms.repository.LoginRepository;
import org.silvachristian.searchfilms.services.MovieServices;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MovieController {

    private final MovieServices movieServices;
    private final LoginRepository loginRepository;

    MovieController(MovieServices filmServices, LoginRepository loginRepository) {
        this.movieServices = filmServices;
        this.loginRepository = loginRepository;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        model.addAttribute("movie", new MovieEntity());
        model.addAttribute("username", userDetails.getUsername());

        return "movies/home";
    }

    @PostMapping("/home")
    public String postHome(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam String movieTitle, Model model) {

        MovieEntity movie = movieServices.findByTitle(
                movieTitle, loginRepository.findUserByUsername(userDetails.getUsername())
        );

        model.addAttribute("movie", movie);
        model.addAttribute("username", userDetails.getUsername());

        return "movies/home";
    }

    @GetMapping("/favorites")
    public String favorites(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        model.addAttribute("username", userDetails.getUsername());

        return "movies/favorites";
    }

    @PostMapping("/favorites")
    public String favorites(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam String movieGenre, Model model) {

        List<FavoritesInfo> movieFavorites = movieServices.showFavorites(
                movieGenre, loginRepository.findUserByUsername(userDetails.getUsername())
        );

        if (movieFavorites.isEmpty()) {
            model.addAttribute("movies", null);
        } else {
            model.addAttribute("movies", movieFavorites);
        }
        model.addAttribute("username", userDetails.getUsername());


        return "movies/favorites";
    }
}
