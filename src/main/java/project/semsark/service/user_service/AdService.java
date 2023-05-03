package project.semsark.service.user_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;
import project.semsark.jwt.JwtUtil;
import project.semsark.mapper.AdMapper;
import project.semsark.model.entity.Building;
import project.semsark.model.entity.User;
import project.semsark.model.request_body.AdRequest;
import project.semsark.model.response_body.AdResponse;
import project.semsark.repository.BuildingRepository;
import project.semsark.repository.UserRepository;
import project.semsark.validation.AdValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdService {
    @Autowired
    AdValidator adValidator;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdMapper adMapper;
    @Autowired
    JwtUtil jwtUtil;

    public void createAd(AdRequest adRequest) {
        Building building = buildingRepository.getBuildingByLangAndLat(adRequest.getLang(), adRequest.getLat());
        while (building != null) {
            adRequest.setLang(adRequest.getLang() + 1);
            adRequest.setLat(adRequest.getLat() + 1);
            building = buildingRepository.getBuildingByLangAndLat(adRequest.getLang(), adRequest.getLat());
        }
        Building ad = new Building();
        adMapper.mapTo(adRequest, ad);
        User user = jwtUtil.getUserDataFromToken();

        ad.setUserId(user.getId());
        Building myAd = buildingRepository.save(ad);
        user.getMyAds().add(myAd);
        userRepository.save(user);
    }

    public void updateAd(AdRequest adRequest, long id) {
        Optional<Building> building = buildingRepository.findById(id);
        if (building.isPresent()) {
            adValidator.valid(id);
            adMapper.mapTo(adRequest, building.get());
            buildingRepository.save(building.get());
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, HelperMessage.BUILDING_NOT_FOUND);
    }


    /////////////////////////////////////////////////////////////////

    public List<Building> getAllAds() {
       return buildingRepository.findAll();
    }

    public List<Building> getMyAds() {
        User user = jwtUtil.getUserDataFromToken();
        List<Building> ads = userRepository.findByEmail(user.getEmail()).get().getMyAds();

        return new ArrayList<>(ads);
    }

    public AdResponse getAd(Long id){
       Optional<Building> building = buildingRepository.findById(id);
       if(building.isPresent()) {
           Building build=building.get();

           build.setViews(build.getViews()+1);

           return adMapper.mapTo( buildingRepository.save(build));
       }
       else
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,HelperMessage.DONT_HAVE_BUILDING);
    }

    public void rateUser(String ownerEmail,Double rate){
       Optional<User> user= userRepository.findByEmail(ownerEmail);

       if(user.isPresent()){
           User user1=user.get();
           user1.setRateCounter(user1.getRateCounter()+1);
           user1.setRateSum(user1.getRateSum()+rate);

           user1.setRate((user1.getRateSum())/(user1.getRateCounter()));
           userRepository.save(user1);
       }else
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,HelperMessage.USER_NOT_FOUND);

    }





}
