package com.trip.advisor.recommendation.service.service;

import com.trip.advisor.common.exception.ResourceNotFoundException;
import com.trip.advisor.recommendation.service.exception.RecommendationAlreadyExist;
import com.trip.advisor.recommendation.service.model.dto.RecommendationDTO;
import com.trip.advisor.recommendation.service.model.entity.Recommendation;
import com.trip.advisor.recommendation.service.model.enums.RecommendationType;
import com.trip.advisor.recommendation.service.repository.RecommendationRepository;
import com.trip.advisor.recommendation.service.service.impl.RecommendationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTests {

    @Mock
    RecommendationRepository recommendationRepository;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    private RecommendationDTO recommendationDTO;
    private Recommendation recommendation;

    @BeforeEach
    public void setUp() {

        recommendationDTO = RecommendationDTO.builder()
                .name("Test Recommendation")
                .type("CAFE")
                .city("Test City")
                .address("Test Address")
                .description("Test Description")
                .rating(5.00)
                .website("Test Website")
                .contactNumber("885504")
                .email("Test@Email.com")
                .openingHours("00-13")
                .build();

        recommendation = Recommendation.builder()
                .name("Test Recommendation")
                .type(RecommendationType.CAFE)
                .city("Test City")
                .address("Test Address")
                .description("Test Description")
                .rating(5.00)
                .website("Test Website")
                .contactNumber("885504")
                .email("Test@Email.com")
                .openingHours("00-13")
                .build();
    }

    @Test
    public void testCreateRecommendation_Success() {
        // Arrange
        when(recommendationRepository.findByNameAndCity(recommendationDTO.getName(), recommendationDTO.getCity()))
                .thenReturn(Optional.empty());
        when(recommendationRepository.save(any(Recommendation.class))).thenReturn(recommendation);

        // Act
        recommendationService.createRecommendation(recommendationDTO);

        // Assert
        verify(recommendationRepository).save(any(Recommendation.class));
    }

    @Test
    public void testCreateRecommendation_AlreadyExists() {
        // Arrange
        when(recommendationRepository.findByNameAndCity(recommendationDTO.getName(), recommendationDTO.getCity()))
                .thenReturn(Optional.of(recommendation));

        // Act & Assert
        assertThrows(RecommendationAlreadyExist.class, () -> {
            recommendationService.createRecommendation(recommendationDTO);
        });
    }

    @Test
    public void testUpdateRecommendation_Success() {
        // Arrange
        when(recommendationRepository.findByNameAndCity(recommendationDTO.getName(), recommendationDTO.getCity()))
                .thenReturn(Optional.of(recommendation));
        when(recommendationRepository.save(any(Recommendation.class))).thenReturn(recommendation);

        // Act
        boolean result = recommendationService.updateRecommendation(recommendationDTO);

        // Assert
        assertTrue(result);
        verify(recommendationRepository).save(any(Recommendation.class));
    }

    @Test
    public void testUpdateRecommendation_NotFound() {
        // Arrange
        when(recommendationRepository.findByNameAndCity(recommendationDTO.getName(), recommendationDTO.getCity()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            recommendationService.updateRecommendation(recommendationDTO);
        });
    }

    @Test
    public void testGetRecommendationByCity_Success() {
        // Arrange
        List<Recommendation> recommendations = Collections.singletonList(recommendation);
        when(recommendationRepository.findAllByCity("Test City")).thenReturn(Optional.of(recommendations));

        // Act
        List<RecommendationDTO> result = recommendationService.getRecommendationByCity("Test City");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetRecommendationByCity_NotFound() {
        // Arrange
        when(recommendationRepository.findAllByCity("Test City")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            recommendationService.getRecommendationByCity("Test City");
        });
    }

    @Test
    public void testGetRecommendationByCityAndType_Success() {
        // Arrange
        List<Recommendation> recommendations = Collections.singletonList(recommendation);
        when(recommendationRepository.findAllByCityAndType("Test City", RecommendationType.CAFE))
                .thenReturn(Optional.of(recommendations));

        // Act
        List<RecommendationDTO> result = recommendationService.getRecommendationByCityAndType("Test City", "CAFE");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetRecommendationByCityAndType_NotFound() {
        // Arrange
        when(recommendationRepository.findAllByCityAndType("Test City", RecommendationType.CAFE))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            recommendationService.getRecommendationByCityAndType("Test City", "CAFE");
        });
    }

    @Test
    public void testDeleteByCityAndName_Success() {
        // Arrange
        when(recommendationRepository.findByNameAndCity("Test Recommendation", "Test City"))
                .thenReturn(Optional.of(recommendation));
        doNothing().when(recommendationRepository).delete(any(Recommendation.class));

        // Act
        Boolean result = recommendationService.deleteByCityAndName("Test City", "Test Recommendation");

        // Assert
        assertTrue(result);
        verify(recommendationRepository).delete(any(Recommendation.class));
    }

    @Test
    public void testDeleteByCityAndName_NotFound() {
        // Arrange
        when(recommendationRepository.findByNameAndCity("Test Recommendation", "Test City"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            recommendationService.deleteByCityAndName("Test City", "Test Recommendation");
        });
    }



}
