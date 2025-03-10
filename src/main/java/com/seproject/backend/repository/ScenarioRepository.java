package com.seproject.backend.repository;

import com.seproject.backend.entity.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario,Integer> {
}
