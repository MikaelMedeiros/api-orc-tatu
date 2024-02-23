package com.codejokers.orctatu.repository;

import com.codejokers.orctatu.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findAllByGoogleId(final String googleId);
}