package com.incidentclassifier.service;


import com.incidentclassifier.dto.DashboardResponse;
import com.incidentclassifier.repository.ClassifiedChunkRepository;
import com.incidentclassifier.repository.DocumentRepository;
import com.incidentclassifier.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    private final DocumentRepository documentRepository;
    private final ClassifiedChunkRepository chunkRepository;
    private final TopicRepository topicRepository;

    public DashboardService(DocumentRepository documentRepository,
                            ClassifiedChunkRepository chunkRepository,
                            TopicRepository topicRepository) {
        this.documentRepository = documentRepository;
        this.chunkRepository = chunkRepository;
        this.topicRepository = topicRepository;
    }

    public DashboardResponse getDashboardStats() {
        long totalDocuments = documentRepository.count();
        long totalChunks = chunkRepository.count();

        Map<String, Long> distribution = new HashMap<>();

        List<com.incidentclassifier.entity.Topic> topics = topicRepository.findAll();
        for (com.incidentclassifier.entity.Topic topic : topics) {
            long count = chunkRepository.countByAssignedTopicId(topic.getId());
            distribution.put(topic.getTitle(), count);
        }

        long unclassifiedCount = chunkRepository.countByAssignedTopicIsNull();
        distribution.put("UNCLASSIFIED", unclassifiedCount);

        return new DashboardResponse(totalDocuments, totalChunks, distribution);
    }
}
