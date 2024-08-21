package com.trip.advisor.recommendation.service.impl;


import com.trip.advisor.common.exception.ResourceNotFoundException;
import com.trip.advisor.recommendation.service.exception.RecommendationAlreadyExist;
import com.trip.advisor.recommendation.service.mapper.RecommendationMapper;
import com.trip.advisor.recommendation.service.model.dto.RecommendationDTO;
import com.trip.advisor.recommendation.service.model.entity.Recommendation;
import com.trip.advisor.recommendation.service.repository.RecommendationRepository;
import com.trip.advisor.recommendation.service.service.impl.RecommendationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RecommendationServiceImplTest  {


    @Mock
    private RecommendationMapper recommendationMapper;

    @Mock
    private RecommendationRepository recommendationRepository;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRecommendation_ShouldCreateWhenNotExist() {
        RecommendationDTO recommendationDTO = RecommendationDTO.builder()
                .name("one")
                .type("hotel")
                .city("Varna")
                .address("new address 01")
                .description("description")
                .rating(2.3)
                .website("www.google.com")
                .contactNumber("3535")
                .email("email@email.com")
                .openingHours("23-2")
                .build();
        Recommendation recommendation = new Recommendation();

        when(recommendationRepository.findByNameAndCity(anyString(), anyString())).thenReturn(Optional.empty());
        when(recommendationMapper.mapRecommendationDTOToRecommendation(any(RecommendationDTO.class))).thenReturn(recommendation);

        recommendationService.createRecommendation(recommendationDTO);

        verify(recommendationRepository, times(1)).save(recommendation);
    }

    @Test
    void createRecommendation_ShouldThrowExceptionWhenExist() {
        RecommendationDTO recommendationDTO = RecommendationDTO.builder()
                .name("one")
                .type("hotel")
                .city("Varna")
                .address("new address 01")
                .description("description")
                .rating(2.3)
                .website("www.google.com")
                .contactNumber("3535")
                .email("email@email.com")
                .openingHours("23-2")
                .build();
        Recommendation recommendation = new Recommendation();

        when(recommendationRepository.findByNameAndCity(anyString(), anyString())).thenReturn(Optional.of(recommendation));

        assertThrows(RecommendationAlreadyExist.class, () -> recommendationService.createRecommendation(recommendationDTO));

        verify(recommendationRepository, never()).save(any(Recommendation.class));
    }

    @Test
    void updateRecommendation_ShouldUpdateWhenExist() {
        RecommendationDTO recommendationDTO = RecommendationDTO.builder()
                .name("one")
                .type("hotel")
                .city("Varna")
                .address("new address 01")
                .description("description")
                .rating(2.3)
                .website("www.google.com")
                .contactNumber("3535")
                .email("email@email.com")
                .openingHours("23-2")
                .build();        Recommendation recommendation = new Recommendation();
        Recommendation updatedRecommendation = new Recommendation();

        when(recommendationRepository.findByNameAndCity(anyString(), anyString())).thenReturn(Optional.of(recommendation));
        when(recommendationMapper.mapRecommendationDTOToRecommendation(any(RecommendationDTO.class))).thenReturn(updatedRecommendation);

        boolean result = recommendationService.updateRecommendation(recommendationDTO);

        assertTrue(result);
        verify(recommendationRepository, times(1)).save(updatedRecommendation);
    }

    @Test
    void updateRecommendation_ShouldThrowExceptionWhenNotExist() {
        RecommendationDTO recommendationDTO = RecommendationDTO.builder()
                .name("one")
                .type("hotel")
                .city("Varna")
                .address("new address 01")
                .description("description")
                .rating(2.3)
                .website("www.google.com")
                .contactNumber("3535")
                .email("email@email.com")
                .openingHours("23-2")
                .build();
        when(recommendationRepository.findByNameAndCity(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recommendationService.updateRecommendation(recommendationDTO));

        verify(recommendationRepository, never()).save(any(Recommendation.class));
    }

    @Test
    void getRecommendationByCity_ShouldReturnRecommendationsWhenExist() {
        String city = "City";
        List<Recommendation> recommendations = Collections.singletonList(new Recommendation());
        List<RecommendationDTO> recommendationDTOS = Collections.singletonList(new RecommendationDTO());

        when(recommendationRepository.findAllByCity(anyString())).thenReturn(Optional.of(recommendations));
        when(recommendationMapper.mapRecommendationToRecommendationDTO(anyList())).thenReturn(recommendationDTOS);

        List<RecommendationDTO> result = recommendationService.getRecommendationByCity(city);

        assertEquals(recommendationDTOS, result);
    }

    @Test
    void getRecommendationByCity_ShouldThrowExceptionWhenNotExist() {
        String city = "City";

        when(recommendationRepository.findAllByCity(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recommendationService.getRecommendationByCity(city));
    }

    @Test
    void getRecommendationByCityAndType_ShouldReturnRecommendationsWhenExist() {
        String city = "City";
        String type = "Type";
        List<Recommendation> recommendations = Collections.singletonList(new Recommendation());
        List<RecommendationDTO> recommendationDTOS = Collections.singletonList(new RecommendationDTO());

        when(recommendationRepository.findAllByCityAndType(anyString(), anyString())).thenReturn(Optional.of(recommendations));
        when(recommendationMapper.mapRecommendationToRecommendationDTO(anyList())).thenReturn(recommendationDTOS);

        List<RecommendationDTO> result = recommendationService.getRecommendationByCityAndType(city, type);

        assertEquals(recommendationDTOS, result);
    }

    @Test
    void getRecommendationByCityAndType_ShouldThrowExceptionWhenNotExist() {
        String city = "City";
        String type = "Type";

        when(recommendationRepository.findAllByCityAndType(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recommendationService.getRecommendationByCityAndType(city, type));
    }

    @Test
    void deleteByCityAndName_ShouldDeleteWhenExist() {
        String city = "City";
        String name = "Name";
        Recommendation recommendation = new Recommendation();

        when(recommendationRepository.findByNameAndCity(anyString(), anyString())).thenReturn(Optional.of(recommendation));

        boolean result = recommendationService.deleteByCityAndName(city, name);

        assertTrue(result);
        verify(recommendationRepository, times(1)).delete(recommendation);
    }

    @Test
    void deleteByCityAndName_ShouldThrowExceptionWhenNotExist() {
        String city = "City";
        String name = "Name";

        when(recommendationRepository.findByNameAndCity(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recommendationService.deleteByCityAndName(city, name));

        verify(recommendationRepository, never()).delete(any(Recommendation.class));
    }


}
