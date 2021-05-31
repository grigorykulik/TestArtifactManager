package com.example.application.backend.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "release_container")
public class ReleaseContainer extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "releaseContainer", fetch = FetchType.EAGER)
    private List<TestCase> testCases = new LinkedList<>();

    public ReleaseContainer() {
    }

    public ReleaseContainer(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }
}
