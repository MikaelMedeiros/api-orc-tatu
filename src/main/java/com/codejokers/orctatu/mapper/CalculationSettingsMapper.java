package com.codejokers.orctatu.mapper;

import com.codejokers.orctatu.dto.CalculationSettingsDTO;
import com.codejokers.orctatu.entity.CalculationSettings;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CalculationSettingsMapper {

    public abstract CalculationSettings toCalculationSettings(final CalculationSettingsDTO calculationSettingsDTO);

    public abstract CalculationSettingsDTO toCalculationSettingsDTO(final CalculationSettings calculationSettings);
}