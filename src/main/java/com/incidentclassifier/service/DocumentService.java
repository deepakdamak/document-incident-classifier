package com.incidentclassifier.service;


import com.incidentclassifier.dto.ClassificationResultResponse;
import com.incidentclassifier.dto.DocumentResultsResponse;
import com.incidentclassifier.entity.ClassifiedChunk;
import com.incidentclassifier.entity.Document;
import com.incidentclassifier.entity.Topic;
import com.incidentclassifier.exception.EntityNotFoundException;
import com.incidentclassifier.repository.ClassifiedChunkRepository;
import com.incidentclassifier.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final ClassifiedChunkRepository chunkRepository;
    private final DocumentProcessingService processingService;

    public DocumentService(DocumentRepository documentRepository,
                           ClassifiedChunkRepository chunkRepository,
                           DocumentProcessingService processingService) {
        this.documentRepository = documentRepository;
        this.chunkRepository = chunkRepository;
        this.processingService = processingService;
    }

    @Transactional
    public Document uploadDocument(MultipartFile file, String rawText) throws IOException {
        String extractedText = processingService.extractTextFromFile(file, rawText);
        Document document = new Document(extractedText);
        document = documentRepository.save(document);
        processingService.processDocument(document, extractedText);
        return document;
    }

    public Document getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id: " + id));
    }

    public DocumentResultsResponse getResults(Long documentId) {
        Document document = getDocument(documentId);
        List<ClassifiedChunk> chunks = chunkRepository.findByDocumentId(documentId);
        List<ClassificationResultResponse> resultResponses = chunks.stream()
                .map(chunk -> {
                    String topicTitle = chunk.getAssignedTopic() != null ? chunk.getAssignedTopic().getTitle() : "UNCLASSIFIED";
                    return new ClassificationResultResponse(chunk.getTextChunk(), topicTitle, chunk.getConfidenceScore());
                })
                .collect(Collectors.toList());
        return new DocumentResultsResponse(documentId, resultResponses);
    }
}
