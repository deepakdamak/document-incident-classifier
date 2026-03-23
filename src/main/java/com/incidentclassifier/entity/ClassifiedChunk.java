package com.incidentclassifier.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "classified_chunks")
public class ClassifiedChunk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String textChunk;

    @ManyToOne
    @JoinColumn(name = "assigned_topic_id")
    private Topic assignedTopic;

    private BigDecimal confidenceScore;

    private LocalDateTime createdAt;


    public ClassifiedChunk() {}

    public ClassifiedChunk(Document document, String textChunk, Topic assignedTopic, BigDecimal confidenceScore) {
        this.document = document;
        this.textChunk = textChunk;
        this.assignedTopic = assignedTopic;
        this.confidenceScore = confidenceScore;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getTextChunk() {
        return textChunk;
    }

    public void setTextChunk(String textChunk) {
        this.textChunk = textChunk;
    }

    public Topic getAssignedTopic() {
        return assignedTopic;
    }

    public void setAssignedTopic(Topic assignedTopic) {
        this.assignedTopic = assignedTopic;
    }

    public BigDecimal getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(BigDecimal confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
