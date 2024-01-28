package com.codejokers.orctatu.repository;

import com.codejokers.orctatu.entity.CalculationSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalculationSettingsRepository extends JpaRepository<CalculationSettings, Long> {

    Optional<CalculationSettings> findById(final String id);
}