package com.trip.advisor.user.service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponseDTO extends AbstractResponseDTO {

    private String jwt;

    public AuthenticationResponseDTO(String message, boolean succeeded, String jwt) {
        super(message, succeeded);
        this.jwt = jwt;
    }
}
