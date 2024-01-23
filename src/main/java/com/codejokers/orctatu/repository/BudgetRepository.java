package com.codejokers.orctatu.repository;

import com.codejokers.orctatu.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {}