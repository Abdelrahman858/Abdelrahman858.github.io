package project.semsark.service.auth_service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import project.semsark.model.request_body.MainProfileRequest;
import project.semsark.model.entity.Profile;
import project.semsark.model.entity.Role;
import project.semsark.model.enums.Roles;
import project.semsark.repository.MainProfileRepository;
import project.semsark.repository.RoleRepository;
import project.semsark.service.MainProfileService;

import java.util.ArrayList;
import java.util.List;


@Component
public class AddRolesService {
    @Autowired
    MainProfileRepository profileRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    MainProfileService mainProfileService;

    @EventListener
    public void insertProfile(ApplicationReadyEvent event) {


        Role roleTemp = roleRepository.getRoleByName(Roles.ROLE_ADMIN.name());

        if (roleTemp == null) {
            roleTemp = new Role();
            roleTemp.setName(Roles.ROLE_ADMIN.name());
            roleRepository.save(roleTemp);
        }
        roleTemp = roleRepository.getRoleByName(Roles.ROLE_USER.name());

        if (roleTemp == null) {
            roleTemp = new Role();
            roleTemp.setName(Roles.ROLE_USER.name());
            roleRepository.save(roleTemp);
        }

        Profile temp1 = profileRepository.findProfileByName("Admin");
        if (temp1 == null) {
            List<Long> ids = new ArrayList<>();
            ids.add(roleRepository.getRoleByName(Roles.ROLE_ADMIN.name()).getId());
            MainProfileRequest profileRequest = MainProfileRequest.builder()
                    .name("Admin")
                    .rolesIds(ids)
                    .build();
            mainProfileService.createProfile(profileRequest);

        }
        temp1 = profileRepository.findProfileByName("User");
        if (temp1 == null) {
            List<Long> ids = new ArrayList<>();
            ids.add(roleRepository.getRoleByName(Roles.ROLE_USER.name()).getId());
            MainProfileRequest profileRequest = MainProfileRequest.builder()
                    .name("User")
                    .rolesIds(ids)
                    .build();
            mainProfileService.createProfile(profileRequest);
        }
        temp1 = profileRepository.findProfileByName("SuperAdmin");
        if (temp1 == null) {
            List<Long> ids = new ArrayList<>();
            ids.add(roleRepository.getRoleByName(Roles.ROLE_USER.name()).getId());
            ids.add(roleRepository.getRoleByName(Roles.ROLE_ADMIN.name()).getId());
            MainProfileRequest profileRequest = MainProfileRequest.builder()
                    .name("SuperAdmin")
                    .rolesIds(ids)
                    .build();
            mainProfileService.createProfile(profileRequest);
        }
    }

}