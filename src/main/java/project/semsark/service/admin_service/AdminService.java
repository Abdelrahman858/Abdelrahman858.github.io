package project.semsark.service.admin_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;
import project.semsark.jwt.JwtUtil;
import project.semsark.mapper.AdMapper;
import project.semsark.mapper.UserDetailsMapper;
import project.semsark.model.entity.Building;
import project.semsark.model.entity.Profile;
import project.semsark.model.entity.User;
import project.semsark.model.enums.ProfileName;
import project.semsark.model.response_body.UserResponse;
import project.semsark.repository.BuildingRepository;
import project.semsark.repository.MainProfileRepository;
import project.semsark.repository.UserRepository;
import project.semsark.validation.AdValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailsMapper mapper;
    @Autowired
    MainProfileRepository profileRepository;

    @Autowired
    AdValidator adValidator;
    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    AdMapper adMapper;
    @Autowired
    JwtUtil jwtUtil;


    public void deleteUser(String email){
        Optional<User>user=userRepository.findByEmail(email);

        if(user.isPresent()){
            userRepository.deleteById(user.get().getId());
        }else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,HelperMessage.USER_NOT_FOUND);
    }

    public void deleteAd(long id) {
        User user = jwtUtil.getUserDataFromToken();
        Optional<Building> build = buildingRepository.findById(id);
        if (build.isPresent()) {
            adValidator.valid(id);
            Building building = build.get();
            userRepository.findByEmail(user.getEmail()).get().getMyAds().remove(building);
            buildingRepository.deleteById(id);
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, HelperMessage.BUILDING_NOT_FOUND);
    }

    public void suspendUser(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            user.get().setSuspended(true);
            userRepository.save(user.get());
        }else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, HelperMessage.USER_NOT_FOUND);
    }

    public void unSuspendUser(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            user.get().setSuspended(false);
            userRepository.save(user.get());
        }else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, HelperMessage.USER_NOT_FOUND);

    }

    public void verifyUser(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            user.get().setVerifyID(true);
            userRepository.save(user.get());
        }else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, HelperMessage.USER_NOT_FOUND);
    }

    public void unVerifyUser(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            user.get().setVerifyID(false);
            userRepository.save(user.get());
        }else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, HelperMessage.USER_NOT_FOUND);

    }
    ////////////////////////////////////////////////////////////////////
   public List<UserResponse>getAllUsers(){
        List<UserResponse>list=new ArrayList<>();

       Profile profile=profileRepository.findProfileByName(ProfileName.User.name());

        for(User user:userRepository.findByProfile(profile))
            list.add(mapper.mapTo(user));

        return list;
    }

    public List<UserResponse>getPendingUsers(){
        List<UserResponse>list=new ArrayList<>();

        Profile profile=profileRepository.findProfileByName(ProfileName.User.name());

        for(User user:userRepository.findByProfile(profile)) {
            if(!(user.isVerifyID()))
                 list.add(mapper.mapTo(user));
        }

        return list;
    }

}
