package project.semsark.mapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.semsark.model.request_body.UserUpdate;
import project.semsark.model.entity.User;
import project.semsark.repository.MainProfileRepository;

import java.util.Objects;

@Component
@Setter
@Getter
public class UserUpdateMapper {
    @Autowired
    MainProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    public void mapTo(UserUpdate userDetailsDTO, User user) {

        if (valid(userDetailsDTO.getUsername()) ) {
            user.setUsername(userDetailsDTO.getUsername());
        }
        if (valid(userDetailsDTO.getPassword())) {
            user.setPassword(bcryptEncoder.encode(userDetailsDTO.getPassword()));
        }
        if (valid(userDetailsDTO.getImg())) {
            user.setImg(userDetailsDTO.getImg());
        }
        if (valid(userDetailsDTO.getGender())) {
            user.setGender(userDetailsDTO.getGender());
        }


        if (valid(userDetailsDTO.getPhone())) {
            if (!Objects.equals(userDetailsDTO.getPhone(), "0"))
                user.setPhone(userDetailsDTO.getPhone());
        }
    }
    boolean valid(String obj){
        return (!obj.equals(""));
    }

}
