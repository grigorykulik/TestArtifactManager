package com.example.application.backend.service;

import com.example.application.backend.entity.ReleaseContainer;
import com.example.application.backend.repository.ReleaseContainerRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReleaseContainerService {

    private ReleaseContainerRepository releaseContainerRepository;

    public ReleaseContainerService(ReleaseContainerRepository releaseContainerRepository) {
        this.releaseContainerRepository = releaseContainerRepository;
    }

    public List<ReleaseContainer> findAll() {
        return releaseContainerRepository.findAll();
    }

    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(releaseContainer ->
                stats.put(releaseContainer.getName(), releaseContainer.getTestCases().size()));
        return stats;
    }
}
