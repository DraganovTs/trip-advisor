package com.trip.advisor.recommendation.service.service.impl;

import com.trip.advisor.common.exception.ResourceNotFoundException;
import com.trip.advisor.recommendation.service.exception.RecommendationAlreadyExist;
import com.trip.advisor.recommendation.service.mapper.RecommendationMapper;
import com.trip.advisor.recommendation.service.model.dto.RecommendationDTO;
import com.trip.advisor.recommendation.service.model.entity.Recommendation;
import com.trip.advisor.recommendation.service.repository.RecommendationRepository;
import com.trip.advisor.recommendation.service.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationMapper recommendationMapper;
    private final RecommendationRepository recommendationRepository;

    public RecommendationServiceImpl(RecommendationMapper recommendationMapper, RecommendationRepository recommendationRepository) {
        this.recommendationMapper = recommendationMapper;
        this.recommendationRepository = recommendationRepository;
    }

    /**
     * create recommendation from RecommendationDTO
     *
     * @param recommendationDTO
     */
    @Override
    public void createRecommendation(RecommendationDTO recommendationDTO) {

        Recommendation recommendation = recommendationMapper.mapRecommendationDTOToRecommendation(recommendationDTO);
        Optional<Recommendation> optionalRecommendation = recommendationRepository
                .findByNameAndCity(recommendationDTO.getName(), recommendationDTO.getCity());
        if (optionalRecommendation.isPresent()) {
            throw new RecommendationAlreadyExist(String.format("Recommendation already exist whit given name: %s and city: %s",
                    recommendationDTO.getName(), recommendationDTO.getCity()));
        }

        recommendationRepository.save(recommendation);
    }

    /**
     * update recommendation base on recommendationDTO
     *
     * @param recommendationDTO
     * @return
     */
    @Override
    public boolean updateRecommendation(RecommendationDTO recommendationDTO) {

        Recommendation recommendation = recommendationRepository.findByNameAndCity(recommendationDTO.getName(),
                recommendationDTO.getCity()).orElseThrow(
                () -> new ResourceNotFoundException("Recommendation", "Name and City",
                        String.join(recommendationDTO.getName(), recommendationDTO.getCity())));

        Recommendation updatedRecommendation = recommendationMapper.mapRecommendationDTOToRecommendation(recommendationDTO);
        updatedRecommendation.setId(recommendation.getId());
        recommendationRepository.save(updatedRecommendation);

        return true;
    }

    /**
     * @param city
     * @return list of recommendations based of given city
     */
    @Override
    public List<RecommendationDTO> getRecommendationByCity(String city) {
        List<Recommendation> recommendationsInCity = recommendationRepository.findAllByCity(city)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Recommendation", "City", city));
        return RecommendationMapper.mapRecommendationToRecommendationDTO(recommendationsInCity);
    }

    /**
     * @param city
     * @param type
     * @return list of recommendations based of given city and type of recommendation
     */
    @Override
    public List<RecommendationDTO> getRecommendationByCityAndType(String city, String type) {
        List<Recommendation> recommendationsInCity = recommendationRepository.findAllByCityAndType(city, type)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Recommendation", "City and Type",
                                String.join(city, type)));
        return RecommendationMapper.mapRecommendationToRecommendationDTO(recommendationsInCity);
    }

    /**
     * @param city
     * @param name
     * @return boolean is deleted
     */
    @Override
    public Boolean deleteByCityAndName(String city, String name) {

        Recommendation recommendation = recommendationRepository.findByNameAndCity(name, city)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Recommendation", "Name and City",
                                String.join(name, city)));

        recommendationRepository.delete(recommendation);

        return true;
    }
}
