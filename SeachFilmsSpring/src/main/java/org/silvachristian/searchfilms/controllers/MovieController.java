package org.silvachristian.searchfilms.controllers;

import org.silvachristian.searchfilms.dto.MovieRequestDTO;
import org.silvachristian.searchfilms.entity.FavoritesInfo;
import org.silvachristian.searchfilms.entity.MovieEntity;
import org.silvachristian.searchfilms.repository.LoginRepository;
import org.silvachristian.searchfilms.services.MovieServices;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

    private final MovieServices movieServices;
    private final LoginRepository loginRepository;

    MovieController(MovieServices filmServices, LoginRepository loginRepository) {
        this.movieServices = filmServices;
        this.loginRepository = loginRepository;
    }

    @PostMapping("/movie")
    public MovieEntity postHome(@RequestBody MovieRequestDTO movieReq) {
        System.out.println("Searching movie: " + movieReq.title());
        return movieServices.findByTitle(
                movieReq.title(), loginRepository.findUserByUsername(movieReq.username())
        );
    }

    @GetMapping("/favorites")
    public List<FavoritesInfo> favorites(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestBody String genre) {

        return movieServices.showFavorites(
                genre, loginRepository.findUserByUsername(userDetails.getUsername())
        );
    }


}
