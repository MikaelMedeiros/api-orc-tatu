package com.codejokers.orctatu.mapper;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.codejokers.orctatu.entity.Budget;
import com.codejokers.orctatu.enums.Status;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class BudgetMapper {

    public abstract Budget toBudget(final BudgetDTO budgetDTO);

    @AfterMapping
    void setDefaultValue(@MappingTarget final Budget budget) {
        if (budget.getStatus() == null) budget.setStatus(Status.BUDGETED);
    }
}