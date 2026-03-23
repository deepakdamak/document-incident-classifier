package com.incidentclassifier.repository;


import com.incidentclassifier.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByTitle(String title);
    @Query(value = "SELECT * FROM topics ORDER BY id LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Topic> findPaginated(int limit, int offset);

    @Query(value = "SELECT COUNT(*) FROM topics", nativeQuery = true)
    long countTotal();
}
