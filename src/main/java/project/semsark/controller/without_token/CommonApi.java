package project.semsark.controller.without_token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.semsark.model.entity.Building;
import project.semsark.service.user_service.AdService;

import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonApi {
    @Autowired
    private AdService adService;

    @GetMapping("/getAllAds")
    List<Building> getAllAds(){
        return adService.getAllAds();
    }
}
