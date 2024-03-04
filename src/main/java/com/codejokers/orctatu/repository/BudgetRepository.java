package com.codejokers.orctatu.repository;

import com.codejokers.orctatu.entity.Budget;
import com.codejokers.orctatu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findAllByUser(final User user);
}