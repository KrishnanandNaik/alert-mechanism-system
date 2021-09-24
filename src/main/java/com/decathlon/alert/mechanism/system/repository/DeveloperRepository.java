package com.decathlon.alert.mechanism.system.repository;

import com.decathlon.alert.mechanism.system.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    @Query(value = "SELECT * FROM developer WHERE team_id = ?1 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Developer findRandomDevByTeamId(Long teamId);
}
