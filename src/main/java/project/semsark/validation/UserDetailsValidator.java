package project.semsark.validation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;
import project.semsark.model.entity.User;
import project.semsark.repository.UserRepository;

import java.util.Optional;

@Component
public class UserDetailsValidator {

    @Autowired
    private UserRepository userRepository;

    public void validate(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.EMAIL_EXIST);
        }
    }
}
