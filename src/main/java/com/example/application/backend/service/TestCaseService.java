package com.example.application.backend.service;

import com.example.application.backend.entity.TestCase;
import com.example.application.backend.repository.ReleaseContainerRepository;
import com.example.application.backend.repository.TestCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TestCaseService {
    private static final Logger LOGGER = Logger.getLogger(TestCaseService.class.getName());
    private TestCaseRepository testCaseRepository;
    private ReleaseContainerRepository releaseContainerRepository;

    public TestCaseService(TestCaseRepository testCaseRepository,
                           ReleaseContainerRepository releaseContainerRepository) {
        this.testCaseRepository = testCaseRepository;
        this.releaseContainerRepository = releaseContainerRepository;
    }

    public List<TestCase> findAll() {
        return testCaseRepository.findAll();
    }

    public List<TestCase> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return testCaseRepository.findAll();
        } else  {
            return  testCaseRepository.search(filterText);
        }
    }

    public long count() {
        return testCaseRepository.count();
    }

    public void delete(TestCase testCase) {
        testCaseRepository.delete(testCase);
    }

    public void save(TestCase testCase) {
        if (testCase == null) {
            LOGGER.log(Level.SEVERE,
                    "Test case is null. Are you sure you have connected your form to the application?");
            return;
        }
        testCaseRepository.save(testCase);
    }

    public List<TestCase> findTestCasesByName(String testCaseName) {
        if(testCaseName == null || testCaseName.isEmpty()) {
            return testCaseRepository.findAll();
        } else  {
            return  testCaseRepository.searchByName(testCaseName);
        }
    }

}
