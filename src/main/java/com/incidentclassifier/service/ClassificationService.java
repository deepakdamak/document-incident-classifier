package com.incidentclassifier.service;


import com.incidentclassifier.entity.Topic;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClassificationService {


    public ClassificationResult classifyChunk(String chunk, List<Topic> topics) {
        String lowerChunk = chunk.toLowerCase();
        Topic bestTopic = null;
        int bestScore = 0;
        int bestKeywordCount = 0;

        for (Topic topic : topics) {
            String[] keywords = topic.getKeywords().split(",");
            int matchCount = 0;
            for (String kw : keywords) {
                if (lowerChunk.contains(kw.trim().toLowerCase())) {
                    matchCount++;
                }
            }
            if (matchCount > bestScore) {
                bestScore = matchCount;
                bestTopic = topic;
                bestKeywordCount = keywords.length;
            }
        }

        if (bestScore == 0) {
            return new ClassificationResult(null, BigDecimal.ZERO);
        }


        BigDecimal confidence = BigDecimal.valueOf(bestScore)
                .divide(BigDecimal.valueOf(bestKeywordCount), 4, RoundingMode.HALF_UP);
        return new ClassificationResult(bestTopic, confidence);
    }

    public static class ClassificationResult {
        private final Topic topic;
        private final BigDecimal confidence;

        public ClassificationResult(Topic topic, BigDecimal confidence) {
            this.topic = topic;
            this.confidence = confidence;
        }

        public Topic getTopic() { return topic; }
        public BigDecimal getConfidence() { return confidence; }
    }
}
