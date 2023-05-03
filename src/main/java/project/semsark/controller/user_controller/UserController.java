package project.semsark.controller.user_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.semsark.jwt.JwtUtil;
import project.semsark.model.entity.Building;
import project.semsark.model.entity.Favourites;
import project.semsark.model.entity.User;
import project.semsark.model.request_body.AdRequest;
import project.semsark.model.response_body.AdResponse;
import project.semsark.service.CustomUserDetailsService;
import project.semsark.service.user_service.AdService;
import project.semsark.service.user_service.FavouriteService;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {
    @Autowired
    private AdService adService;
    @Autowired
    private FavouriteService favouriteService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    ////////////////////////////// ADD //////////////////////////////////////
    @PostMapping("/createAd")
    void createAd(@RequestBody AdRequest adRequest) {
        adService.createAd(adRequest);
    }

    @PostMapping("/addFavourite/{id}")
    void addToFavourite(@PathVariable long id) {
        favouriteService.addToFavourite(id);
    }
    /////////////////////////////// UPDATE ////////////////////////////////////////
    @PatchMapping("/updateAd/{id}")
    void updateAd(@RequestBody AdRequest adRequest, @PathVariable long id) {
        adService.updateAd(adRequest, id);
    }

    @PatchMapping("/updateRate/{ownerEmail}/{rate}")
    void rateUser(@PathVariable String ownerEmail,@PathVariable Double rate) {
        adService.rateUser(ownerEmail,rate);
    }

    ////////////////////////////////// DELETE ///////////////////////////////////////////


    @DeleteMapping("/deleteFavourite/{id}")
    void deleteFavourite(@PathVariable long id) {
        favouriteService.deleteFavourite(id);
    }
    /////////////////////////////// GET ////////////////////////////////////////


    @GetMapping("/getMyAds")
    List<Building> getMyAds() {
        return adService.getMyAds();
    }

    @GetMapping("/building/{id}")
    AdResponse getAd(@PathVariable long id) {
        return adService.getAd(id);
    }

    @GetMapping("/getUser")
    User getUser() {
        return jwtUtil.getUserDataFromToken();
    }

    @GetMapping("/getUser/{id}")
    String getUserById(@PathVariable long id) {
        return customUserDetailsService.getUserDataById(id);
    }

    @GetMapping("/getMyFavourite")
    Favourites getMyFavourites() {
        return favouriteService.getMyFavourites();
    }

    @GetMapping("/getMyFavouriteById/{id}")
    void getMyFavouriteById(@PathVariable long id) {
        favouriteService.getMyFavouriteById(id);
    }
}
