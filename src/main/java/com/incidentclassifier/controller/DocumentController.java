package com.incidentclassifier.controller;


import com.incidentclassifier.dto.ApiResponse;
import com.incidentclassifier.dto.DocumentResultsResponse;
import com.incidentclassifier.entity.Document;
import com.incidentclassifier.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Document>> uploadDocument(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "text", required = false) String text) throws IOException {
        Document document = documentService.uploadDocument(file, text);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Document uploaded and processed", document));
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<ApiResponse<DocumentResultsResponse>> getResults(@PathVariable Long id) {
        DocumentResultsResponse results = documentService.getResults(id);
        return ResponseEntity.ok(ApiResponse.success(results));
    }
}
