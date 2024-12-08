package com.trip.advisor.user.service.controller;

import com.trip.advisor.user.service.model.dto.AuthenticationRequestDTO;
import com.trip.advisor.user.service.model.dto.AuthenticationResponseDTO;
import com.trip.advisor.user.service.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticate")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping
    public ResponseEntity<?> getAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUsername(),
                            authenticationRequestDTO.getPassword()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new AuthenticationResponseDTO("Incorrect username or password",
                    false,
                    ""));
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDTO.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponseDTO("Authentication was successful", true, jwt));
    }
}
