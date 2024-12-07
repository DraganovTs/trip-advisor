package com.trip.advisor.user.service.services;

import com.trip.advisor.user.service.exception.InvalidOperationException;
import com.trip.advisor.user.service.model.dto.UserRegistrationDTO;
import com.trip.advisor.user.service.model.entity.Role;
import com.trip.advisor.user.service.model.entity.User;
import com.trip.advisor.user.service.model.enums.ERole;
import com.trip.advisor.user.service.repository.RoleRepository;
import com.trip.advisor.user.service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

public class UserRegistrationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.existsByUsername(userRegistrationDTO.getUsername()) ||
                userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new InvalidOperationException("User with this username or email already exists.");
        }

        Role role = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new InvalidOperationException("Default user role not found."));

        User user = new User(
                userRegistrationDTO.getUsername(),
                userRegistrationDTO.getEmail(),
                passwordEncoder.encode(userRegistrationDTO.getPassword()),
                new HashSet<>(Arrays.asList(role))
        );

        userRepository.save(user);
    }
}
