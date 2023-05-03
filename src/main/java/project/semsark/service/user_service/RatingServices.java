package project.semsark.service.user_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;
import project.semsark.model.entity.Opinion;
import project.semsark.model.request_body.OpinionRequest;
import project.semsark.repository.OpinionRepo;

import java.util.List;

@Service
public class RatingServices {
    @Autowired
    private OpinionRepo opinionRepo;

    public void addOpinion(OpinionRequest request) {
        if (request.getRate() > 5 || request.getRate() < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.OUT_OF_RANGE);

        opinionRepo.save(Opinion.builder()
                .opinion(request.getOpinion())
                .userEmail(request.getUserEmail())
                .rate(request.getRate())
                .build());
    }

    public List<Opinion> getAllOpinion() {
        return opinionRepo.findAll();
    }
}
