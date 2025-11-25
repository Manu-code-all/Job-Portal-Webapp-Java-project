package com.example.jobportal.model;

import java.time.LocalDateTime;

public class Job {
    private int id;
    private int employerId;
    private String title;
    private String description;
    private String requirements;
    private String salary;
    private String location;
    private String status;
    private LocalDateTime createdAt;
    // getters/setters omitted for brevity
}
