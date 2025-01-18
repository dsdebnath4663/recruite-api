package com.recruitment.service;

import com.recruitment.exception.UserNotFoundException;
import com.recruitment.model.User;
import com.recruitment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user and saves it to the database.
     *
     * @param user the user to be created
     * @return the saved user
     */
    public User createUser(User user) {
        log.info("Attempting to create user with email: {}, password: {}", user.getEmail(),user.getPassword());

        // Check if the password exists and is not empty
        if (StringUtils.hasText(user.getPassword())) {
            log.info("Password provided for user: {}. Encoding password.", user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            log.warn("No password provided for user: {}", user.getEmail());
        }

        // Save the user to the repository
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        log.info("Fetching all users from the database.");
        List<User> users = userRepository.findAll();
        log.info("Retrieved {} users from the database.", users.size());
        return users;
    }

    /**
     * Retrieves  users details from the database ny email .
     *
     * @return a user
     */
    public User getUserDetailsByEmail(String email) {
        log.info("Fetching user with email: {}", email);
        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));
        log.info("User found: {}", user);
        user.setProfile(user.getProfile());
        user.setRole(user.getRole());
        return user;
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(Long id) {
        log.info("Attempting to delete user with ID: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User with ID: {} deleted successfully.", id);
        } else {
            log.warn("No user found with ID: {}. Deletion aborted.", id);
        }
    }

    /**
     * Updates the password of a user identified by email.
     *
     * @param email       the email of the user
     * @param newPassword the new password to set
     */
    public void setPassword(String email, String newPassword) {
        log.info("Attempting to update password for user with email: {}", email);

        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> {
                                      log.error("User not found with email: {}", email);
                                      return new IllegalArgumentException("User not found with email: " + email);
                                  });

        log.info("User found with email: {}. Proceeding to update password.", user.getEmail());
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("Password updated successfully for email: {}", email);
    }
}
