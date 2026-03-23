package com.incidentclassifier.service;


import com.incidentclassifier.entity.ClassifiedChunk;
import com.incidentclassifier.entity.Document;
import com.incidentclassifier.entity.Topic;
import com.incidentclassifier.repository.ClassifiedChunkRepository;
import com.incidentclassifier.util.PdfTextExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class DocumentProcessingService {
    private final PdfTextExtractor pdfExtractor;
    private final ClassificationService classificationService;
    private final TopicService topicService;
    private final ClassifiedChunkRepository chunkRepository;

    public DocumentProcessingService(PdfTextExtractor pdfExtractor,
                                     ClassificationService classificationService,
                                     TopicService topicService,
                                     ClassifiedChunkRepository chunkRepository) {
        this.pdfExtractor = pdfExtractor;
        this.classificationService = classificationService;
        this.topicService = topicService;
        this.chunkRepository = chunkRepository;
    }

    public void processDocument(Document document, String text) {
        List<String> chunks = splitIntoSentences(text);
        List<Topic> topics = topicService.getAllTopics();

        for (String chunk : chunks) {
            if (chunk.trim().isEmpty()) continue;
            ClassificationService.ClassificationResult result = classificationService.classifyChunk(chunk, topics);
            ClassifiedChunk classifiedChunk = new ClassifiedChunk(
                    document,
                    chunk,
                    result.getTopic(),
                    result.getConfidence()
            );
            chunkRepository.save(classifiedChunk);
        }
    }

    private List<String> splitIntoSentences(String text) {
        String[] sentences = text.split("(?<=[.!?])\\s+");
        return Arrays.asList(sentences);
    }

    public String extractTextFromFile(MultipartFile file, String rawText) throws IOException {
        if (file != null && !file.isEmpty()) {
            String filename = file.getOriginalFilename();
            if (filename != null && filename.toLowerCase().endsWith(".pdf")) {
                return pdfExtractor.extractText(file);
            } else {
                return new String(file.getBytes());
            }
        } else if (rawText != null && !rawText.isEmpty()) {
            return rawText;
        } else {
            throw new IllegalArgumentException("No content provided");
        }
    }
}
