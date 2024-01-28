package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.CalculationSettingsDTO;
import com.codejokers.orctatu.entity.CalculationSettings;
import com.codejokers.orctatu.exception.ApplicationException;
import com.codejokers.orctatu.mapper.CalculationSettingsMapper;
import com.codejokers.orctatu.repository.CalculationSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculationSettingsService {

    private final CalculationSettingsMapper calculationSettingsMapper;
    private final CalculationSettingsRepository calculationSettingsRepository;

    public CalculationSettings find(final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        return calculationSettingsRepository.findById(oAuth2AuthenticatedPrincipal.getAttributes().get("sub").toString())
                                            .orElseThrow(() -> new ApplicationException(404, "Não existe uma configuração de cálculos para esse usuário."));
    }

    public CalculationSettings save(final CalculationSettingsDTO calculationSettingsDTO, final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final CalculationSettings calculationSettings = calculationSettingsMapper.toCalculationSettings(calculationSettingsDTO);
        calculationSettings.setId(oAuth2AuthenticatedPrincipal.getAttributes().get("sub").toString());
        return calculationSettingsRepository.save(calculationSettings);
    }

    public CalculationSettings update(final CalculationSettingsDTO calculationSettingsDTO, final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final CalculationSettings calculationSettings = find(oAuth2AuthenticatedPrincipal);
        fillInCalculationSettings(calculationSettings, calculationSettingsDTO);
        return calculationSettingsRepository.save(calculationSettings);
    }

    private void fillInCalculationSettings(final CalculationSettings calculationSettings, final CalculationSettingsDTO calculationSettingsDTO) {
        calculationSettings.setPricePerCentimeter(calculationSettingsDTO.pricePerCentimeter());
        calculationSettings.setStudioPercentage(calculationSettingsDTO.studioPercentage());
        calculationSettings.setParkingCost(calculationSettingsDTO.parkingCost());
        calculationSettings.setMaterialCost(calculationSettingsDTO.materialCost());
        calculationSettings.setCreditCardFee(calculationSettingsDTO.creditCardFee());
    }
}