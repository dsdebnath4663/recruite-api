package com.recruitment.service;


import com.recruitment.model.UserProfile;
import com.recruitment.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public UserProfile createProfile ( UserProfile profile ) {
        return profileRepository.save(profile);
    }

    public List<UserProfile> getAllProfiles () {
        return profileRepository.findAll();
    }

    public UserProfile updateProfile ( Long id , UserProfile updatedProfile ) {
        UserProfile profile = profileRepository.findById(id)
                                               .orElseThrow(
                                                       () -> new RuntimeException("Profile not found with ID: " + id));
        profile.setName(updatedProfile.getName());
        profile.setDescription(updatedProfile.getDescription());
        profile.setPermissions(updatedProfile.getPermissions());
        return profileRepository.save(profile);
    }

    public void deleteProfile ( Long id ) {
        profileRepository.deleteById(id);
    }
}
