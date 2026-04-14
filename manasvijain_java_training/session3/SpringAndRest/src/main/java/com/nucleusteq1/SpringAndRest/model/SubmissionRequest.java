package com.nucleusteq1.SpringAndRest.model;

public class SubmissionRequest {
    private String name;
    private String email;
    private Integer age;

    public SubmissionRequest(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

}
