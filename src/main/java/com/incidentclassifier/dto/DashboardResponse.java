package com.incidentclassifier.dto;


import java.util.Map;

public class DashboardResponse {
    private long totalDocuments;
    private long totalChunks;
    private Map<String, Long> topicDistribution;

    public DashboardResponse() {}
    public DashboardResponse(long totalDocuments, long totalChunks, Map<String, Long> topicDistribution) {
        this.totalDocuments = totalDocuments;
        this.totalChunks = totalChunks;
        this.topicDistribution = topicDistribution;
    }

    public long getTotalDocuments() { return totalDocuments; }
    public void setTotalDocuments(long totalDocuments) { this.totalDocuments = totalDocuments; }
    public long getTotalChunks() { return totalChunks; }
    public void setTotalChunks(long totalChunks) { this.totalChunks = totalChunks; }
    public Map<String, Long> getTopicDistribution() { return topicDistribution; }
    public void setTopicDistribution(Map<String, Long> topicDistribution) { this.topicDistribution = topicDistribution; }
}
