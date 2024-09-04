package com.trip.advisor.recommendation.service.repository;

import com.trip.advisor.recommendation.service.model.entity.Recommendation;
import com.trip.advisor.recommendation.service.model.enums.RecommendationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RecommendationRepositoryTests {

    @Autowired
    private RecommendationRepository recommendationRepository;

    private Recommendation recommendation;

    @BeforeEach
    public void setUp() {
        recommendationRepository.deleteAll();

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

        recommendationRepository.save(recommendation);
    }

    @Test
    public void testSaveRecommendation() {
        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        assertThat(savedRecommendation).isNotNull();
        assertThat(savedRecommendation.getId()).isNotNull();
        assertThat(savedRecommendation.getName()).isEqualTo("Test Recommendation");
    }

    @Test
    public void testFindById() {
        Optional<Recommendation> found = recommendationRepository.findById(recommendation.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Recommendation");
    }

    @Test
    public void testFindByIdNotFound() {
        Optional<Recommendation> found = recommendationRepository.findById(-1L);
        assertThat(found).isNotPresent();
    }

    @Test
    public void testFindByNameAndCity() {
        Optional<Recommendation> found = recommendationRepository.findByNameAndCity("Test Recommendation",
                "Test City");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Recommendation");
        assertThat(found.get().getCity()).isEqualTo("Test City");
    }

    @Test
    public void testFindByNameAndCityNotFound() {
        Optional<Recommendation> found = recommendationRepository.findByNameAndCity("Non-existent Recommendation",
                "Test City");
        assertThat(found).isNotPresent();
    }

    @Test
    public void testFindAllByCity() {
        Recommendation anotherRecommendation = Recommendation.builder()
                .name("Another Recommendation")
                .type(RecommendationType.RESTAURANT)
                .city("Test City")
                .address("Another Address")
                .description("Another Description")
                .rating(4.00)
                .website("Another Website")
                .contactNumber("123456")
                .email("Another@Email.com")
                .openingHours("10-22")
                .build();
        recommendationRepository.save(anotherRecommendation);

        List<Recommendation> found = recommendationRepository.findAllByCity("Test City").orElseThrow();
        assertThat(found).hasSize(2);
        assertThat(found).extracting(Recommendation::getCity).containsOnly("Test City");
    }

    @Test
    public void testFindAllByCityNotFound() {
        List<Recommendation> found = recommendationRepository.findAllByCity("Non-existent City").orElseThrow();
        assertThat(found).isEmpty();
    }

    @Test
    public void testFindAllByCityAndType() {
        Recommendation anotherRecommendation = Recommendation.builder()
                .name("Another Recommendation")
                .type(RecommendationType.CAFE)
                .city("Test City")
                .address("Another Address")
                .description("Another Description")
                .rating(4.00)
                .website("Another Website")
                .contactNumber("123456")
                .email("Another@Email.com")
                .openingHours("10-22")
                .build();
        recommendationRepository.save(anotherRecommendation);

        List<Recommendation> found = recommendationRepository.findAllByCityAndType("Test City",
                RecommendationType.CAFE).orElseThrow();
        assertThat(found).hasSize(2);
        assertThat(found).extracting(Recommendation::getType).containsOnly(RecommendationType.CAFE);
    }

    @Test
    public void testFindAllByCityAndTypeNotFound() {
        List<Recommendation> found = recommendationRepository.findAllByCityAndType("Test City",
                RecommendationType.RESTAURANT).orElseThrow();
        assertThat(found).isEmpty();
    }

    @Test
    public void testUpdateRecommendation() {
        recommendation.setDescription("Updated Description");
        Recommendation updatedRecommendation = recommendationRepository.save(recommendation);
        assertThat(updatedRecommendation).isNotNull();
        assertThat(updatedRecommendation.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    public void testDeleteRecommendation() {
        recommendationRepository.deleteById(recommendation.getId());
        Optional<Recommendation> found = recommendationRepository.findById(recommendation.getId());
        assertThat(found).isNotPresent();
    }
}
