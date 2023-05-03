package project.semsark.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;
import project.semsark.jwt.JwtUtil;
import project.semsark.model.entity.User;
import project.semsark.repository.BuildingRepository;

@Component

public class AdValidator {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    BuildingRepository buildingRepository;

    public void valid(Long id){
        User user = jwtUtil.getUserDataFromToken();
        if(!user.getMyAds().contains(buildingRepository.findById(id).get()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.DONT_HAVE_BUILDING);
    }
}
