package com.incidentclassifier.dto;


import java.util.List;

public class TopicResponse {
    private Long id;
    private String title;
    private List<String> keywords;

    public TopicResponse() {}
    public TopicResponse(Long id, String title, List<String> keywords) {
        this.id = id;
        this.title = title;
        this.keywords = keywords;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }
}
