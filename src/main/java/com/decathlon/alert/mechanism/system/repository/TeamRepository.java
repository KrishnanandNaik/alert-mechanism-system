package com.decathlon.alert.mechanism.system.repository;

import com.decathlon.alert.mechanism.system.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
