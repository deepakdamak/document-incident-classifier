package com.incidentclassifier.controller;


import com.incidentclassifier.dto.ApiResponse;
import com.incidentclassifier.dto.TopicRequest;
import com.incidentclassifier.dto.TopicResponse;
import com.incidentclassifier.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TopicResponse>> createTopic(@Valid @RequestBody TopicRequest request) {
        TopicResponse response = topicService.createTopic(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Topic created", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TopicResponse>>> getAllTopics() {
        List<TopicResponse> responses = topicService.getAllTopics().stream()
                .map(topic -> new TopicResponse(topic.getId(), topic.getTitle(), List.of(topic.getKeywords().split(","))))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TopicResponse>> getTopic(@PathVariable Long id) {
        TopicResponse response = topicService.getTopicResponseById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<TopicResponse>>> getAllTopicsPaginated(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TopicResponse> page = topicService.getAllTopics(pageable);
        return ResponseEntity.ok(ApiResponse.success(page));
    }
}
