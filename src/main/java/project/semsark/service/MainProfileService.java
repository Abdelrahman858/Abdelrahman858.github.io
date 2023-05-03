package project.semsark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;
import project.semsark.model.request_body.MainProfileRequest;
import project.semsark.model.response_body.ProfileResponse;
import project.semsark.model.request_body.RoleDTO;
import project.semsark.model.entity.Profile;
import project.semsark.model.entity.Role;
import project.semsark.repository.MainProfileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainProfileService {

    @Autowired
    private MainProfileRepository profileRepository;

    @Autowired
    private RoleService roleService;


    public ProfileResponse createProfile(MainProfileRequest profileRequest) {

        List<Role> existedRoles = roleService.findRoles(profileRequest.getRolesIds());

        Profile profile = Profile.builder()
                .name(profileRequest.getName())
                .roles(existedRoles)
                .build();

        Profile savedProfile = profileRepository.save(profile);
        return ProfileResponse.builder()
                .id(savedProfile.getId())
                .name(savedProfile.getName())
                .roles(savedProfile.getRoles().stream().map(role -> RoleDTO.builder().name(role.getName()).build())
                        .collect(Collectors.toList()))
                .build();
    }

    public ProfileResponse updateProfile(Long profileId, MainProfileRequest profileRequest) {

        List<Role> existedRoles = roleService.findRoles(profileRequest.getRolesIds());

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("profile with id: %s, not exist", profileId)));

        profile.setName(profileRequest.getName());
        profile.setRoles(existedRoles);

        Profile savedProfile = profileRepository.save(profile);
        return ProfileResponse.builder()
                .id(savedProfile.getId())
                .name(savedProfile.getName())
                .roles(savedProfile.getRoles().stream().map(role -> RoleDTO.builder().name(role.getName()).build())
                        .collect(Collectors.toList()))
                .build();
    }

    public Profile findProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.PROFILE_NOT_FOUND));
    }
}
