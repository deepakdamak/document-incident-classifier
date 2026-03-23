package com.incidentclassifier.service;


import com.incidentclassifier.dto.TopicRequest;
import com.incidentclassifier.dto.TopicResponse;
import com.incidentclassifier.entity.Topic;
import com.incidentclassifier.exception.EntityNotFoundException;
import com.incidentclassifier.repository.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public TopicResponse createTopic(TopicRequest request) {
        String keywordsStr = String.join(",", request.getKeywords());
        Topic topic = new Topic(request.getTitle(), keywordsStr);
        Topic saved = topicRepository.save(topic);
        return new TopicResponse(saved.getId(), saved.getTitle(), Arrays.asList(saved.getKeywords().split(",")));
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + id));
    }

    public TopicResponse getTopicResponseById(Long id) {
        Topic topic = getTopicById(id);
        return new TopicResponse(topic.getId(), topic.getTitle(), Arrays.asList(topic.getKeywords().split(",")));
    }
    public Page<TopicResponse> getAllTopics(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();
        List<Topic> topics = topicRepository.findPaginated(pageSize, offset);
        long total = topicRepository.countTotal();
        List<TopicResponse> responses = topics.stream()
                .map(topic -> new TopicResponse(topic.getId(), topic.getTitle(), Arrays.asList(topic.getKeywords().split(","))))
                .collect(Collectors.toList());
        return new PageImpl<>(responses, pageable, total);
    }
}
