package project.semsark.service.user_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;
import project.semsark.jwt.JwtUtil;
import project.semsark.model.entity.Building;
import project.semsark.model.entity.Favourites;
import project.semsark.model.entity.User;
import project.semsark.repository.BuildingRepository;
import project.semsark.repository.FavouriteRepository;
import project.semsark.repository.UserRepository;
import project.semsark.validation.FavValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService {
    @Autowired
    FavValidator favValidator;
    @Autowired
    FavouriteRepository favouriteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    JwtUtil jwtUtil;

    public void addToFavourite(long id){
        User user = jwtUtil.getUserDataFromToken();
        Optional<Building> building =buildingRepository.findById(id);
        if(building.isPresent()){
            Favourites ff=favouriteRepository.findByUserId(user.getId());
            if(ff!=null) {
                List<Building> list = ff.getBuildings();
                if (!list.contains(building.get())){
                    list.add(building.get());
                    ff.setBuildings(list);
                    favouriteRepository.save(ff);
                    user.setFavourites(ff);
                 }else
                     throw new ResponseStatusException(HttpStatus.FOUND,HelperMessage.ITEM_ALREADY_EXIST);
            }else {
                List<Building> buildingList = new ArrayList<>();
                buildingList.add(building.get());

                Favourites fav = new Favourites();
                fav.setUserId(user.getId());
                fav.setBuildings(buildingList);
                favouriteRepository.save(fav);
                user.setFavourites(fav);
            }

            userRepository.save(user);
        }else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, HelperMessage.BUILDING_NOT_FOUND);

    }

    public void deleteFavourite(long id){
        User user = jwtUtil.getUserDataFromToken();
        Optional<Building> building =buildingRepository.findById(id);

        if (building.isPresent()){
            favValidator.valid(id);
            Favourites fav = favouriteRepository.findByUserId(user.getId());
            if(fav.getBuildings().contains(building.get())) {
                fav.getBuildings().remove(building.get());
                favouriteRepository.save(fav);

            }else
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,HelperMessage.FAVOURITE_NOT_FOUND);
        }else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,HelperMessage.BUILDING_NOT_FOUND);
    }
    ////////////////////////////////////////////////////////////////
    public Favourites getMyFavourites(){
        User user=jwtUtil.getUserDataFromToken();
        return user.getFavourites();
    }

    public void getMyFavouriteById(long id){
        User user=jwtUtil.getUserDataFromToken();
        Optional<Building> building = buildingRepository.findById(id);
        if(!(building.filter(value -> user.getFavourites().getBuildings().contains(value)).isPresent())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,HelperMessage.BUILDING_NOT_FOUND);
        }
    }
}
