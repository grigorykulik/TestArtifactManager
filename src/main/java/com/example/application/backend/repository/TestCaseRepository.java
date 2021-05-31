package com.example.application.backend.repository;

import com.example.application.backend.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    @Query("select t from TestCase t " +
            "where lower(t.execution) like lower(concat('%', :searchTerm, '%')) ")
    List<TestCase> search(@Param("searchTerm") String searchTerm);


    @Query("select t from TestCase t " +
            "where lower(t.name) like lower(concat('%', :searchTerm, '%')) ")
    List<TestCase> searchByName(@Param("searchTerm") String searchTerm);
}
