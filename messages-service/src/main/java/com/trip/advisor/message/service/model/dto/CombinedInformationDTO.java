package com.trip.advisor.message.service.model.dto;

import com.trip.advisor.common.model.dto.AddressDTO;
import com.trip.advisor.common.model.dto.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CombinedInformationDTO {

    private String userName;
    private String userEmailAddress;
    private String accommodationName;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;
}
