package com.incidentclassifier.dto;


import java.util.List;

public class DocumentResultsResponse {
    private Long documentId;
    private List<ClassificationResultResponse> results;

    public DocumentResultsResponse() {}
    public DocumentResultsResponse(Long documentId, List<ClassificationResultResponse> results) {
        this.documentId = documentId;
        this.results = results;
    }

    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }
    public List<ClassificationResultResponse> getResults() { return results; }
    public void setResults(List<ClassificationResultResponse> results) { this.results = results; }
}
