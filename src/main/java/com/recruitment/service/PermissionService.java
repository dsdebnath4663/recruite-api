package com.recruitment.service;

import com.recruitment.dto.Permissions;
import com.recruitment.model.User;
import com.recruitment.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private final UserRepository userRepository;

    public PermissionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Permissions.ModulePermission getModulePermission(Long userId, String moduleName) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return user.getProfile().getPermissions().getModulePermissions()
                   .stream()
                   .filter(permission -> permission.getEntity().equalsIgnoreCase(moduleName))
                   .findFirst()
                   .orElseThrow(() -> new IllegalArgumentException("Permission not found for module: " + moduleName));
    }

    public Permissions.SocialPermissions getSocialPermissions(Long userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return user.getProfile().getPermissions().getSocialPermissions();
    }
}
