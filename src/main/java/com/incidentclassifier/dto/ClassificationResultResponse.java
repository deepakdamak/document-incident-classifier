package com.incidentclassifier.dto;


import java.math.BigDecimal;

public class ClassificationResultResponse {
    private String text;
    private String assignedTopic;
    private BigDecimal confidence;

    public ClassificationResultResponse() {}
    public ClassificationResultResponse(String text, String assignedTopic, BigDecimal confidence) {
        this.text = text;
        this.assignedTopic = assignedTopic;
        this.confidence = confidence;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getAssignedTopic() { return assignedTopic; }
    public void setAssignedTopic(String assignedTopic) { this.assignedTopic = assignedTopic; }
    public BigDecimal getConfidence() { return confidence; }
    public void setConfidence(BigDecimal confidence) { this.confidence = confidence; }
}
