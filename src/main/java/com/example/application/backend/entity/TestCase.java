package com.example.application.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "test_cases")
public class TestCase extends AbstractEntity implements Cloneable {

    public enum Status {
        Pass, InProgress, Failed, Blocked, Aborted, Waiting
    }

    @NotNull
    @NotEmpty
    @Column(name = "execution")
    private String execution = "";

    @NotNull
    @NotEmpty
    @Column(name = "tfk_name")
    private String tfkName = "";

    @NotNull
    @NotEmpty
    @Column(name = "name")
    private String name = "";

    @NotNull
    @NotEmpty
    @Column(name = "crm")
    private String crm = "";

    @Column(name = "comment")
    private String comment = "";

    @Column(name = "test_data")
    private String testData;

    @Column(name = "related_stories")
    private String relatedStories;

    @Column(name = "related_bugs")
    private String relatedBugs;

    @ManyToOne
    @JoinColumn(name = "release_container")
    private ReleaseContainer releaseContainer;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status")
    private TestCase.Status status;

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReleaseContainer getReleaseContainer() {
        return releaseContainer;
    }

    public void setReleaseContainer(ReleaseContainer releaseContainer) {
        this.releaseContainer = releaseContainer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public String getRelatedStories() {
        return relatedStories;
    }

    public void setRelatedStories(String relatedStories) {
        this.relatedStories = relatedStories;
    }

    public String getRelatedBugs() {
        return relatedBugs;
    }

    public void setRelatedBugs(String relatedBugs) {
        this.relatedBugs = relatedBugs;
    }

    public String getTfkName() {
        return tfkName;
    }

    public void setTfkName(String tfkName) {
        this.tfkName = tfkName;
    }

    @Override
    public String toString() {
        return execution + " " + name;
    }

}
