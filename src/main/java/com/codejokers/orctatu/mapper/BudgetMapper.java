package com.codejokers.orctatu.mapper;

import com.codejokers.orctatu.dtos.BudgetDto;
import com.codejokers.orctatu.entities.Budget;
import com.codejokers.orctatu.enums.BudgetStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class BudgetMapper {

    public abstract Budget toBudget(BudgetDto budgetDto);

    @AfterMapping
    void setDefaultValue(@MappingTarget Budget target) {
        if (target.getStatus() == null) {
            target.setStatus(BudgetStatus.ORCADO);
        }
    }
}
