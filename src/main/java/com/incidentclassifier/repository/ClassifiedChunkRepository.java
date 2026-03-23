package com.incidentclassifier.repository;



import com.incidentclassifier.entity.ClassifiedChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassifiedChunkRepository extends JpaRepository<ClassifiedChunk, Long> {
    List<ClassifiedChunk> findByDocumentId(Long documentId);
    long countByAssignedTopicId(Long topicId);
    long countByAssignedTopicIsNull();
}
