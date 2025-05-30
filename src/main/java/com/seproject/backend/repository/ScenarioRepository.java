package com.seproject.backend.repository;

import com.seproject.backend.entity.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioRepository extends JpaRepository<Scenario,Integer> {
    List<Scenario> findAllByHourAndMinute(int hour, int minute);
}
