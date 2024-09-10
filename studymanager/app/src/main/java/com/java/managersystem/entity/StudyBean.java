package com.java.managersystem.entity;

import java.io.Serializable;

public class StudyBean implements Serializable {
    private String studyCode;
    private String name;
    private String course;
    private String score;

    public StudyBean() {

    }

    public StudyBean(String studyCode, String name, String course, String score) {
        this.studyCode = studyCode;
        this.name = name;
        this.course = course;
        this.score = score;
    }

    public String getStudyCode() {
        return studyCode;
    }

    public void setStudyCode(String studyCode) {
        this.studyCode = studyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
