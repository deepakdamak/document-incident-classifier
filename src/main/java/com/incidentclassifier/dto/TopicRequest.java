package com.incidentclassifier.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class TopicRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotEmpty(message = "Keywords list cannot be empty")
    private List<String> keywords;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }
}
