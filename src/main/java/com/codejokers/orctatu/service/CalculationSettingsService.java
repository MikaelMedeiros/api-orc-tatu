package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.CalculationSettingsDTO;
import com.codejokers.orctatu.dto.UserInfoDTO;
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
        return calculationSettingsRepository.findById(getSub(oAuth2AuthenticatedPrincipal))
                                            .orElseThrow(() -> new ApplicationException(404, "Não existe uma configuração de cálculos para esse usuário."));
    }

    public CalculationSettings save(final CalculationSettingsDTO calculationSettingsDTO, final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final CalculationSettings calculationSettings = calculationSettingsMapper.toCalculationSettings(calculationSettingsDTO);
        calculationSettings.setId(getSub(oAuth2AuthenticatedPrincipal));
        return calculationSettingsRepository.save(calculationSettings);
    }

    public CalculationSettings update(final CalculationSettingsDTO calculationSettingsDTO, final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        find(oAuth2AuthenticatedPrincipal);
        return save(calculationSettingsDTO, oAuth2AuthenticatedPrincipal);
    }

    private String getSub(final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        final UserInfoDTO userInfoDTO = (UserInfoDTO) oAuth2AuthenticatedPrincipal.getAttributes().get("userInfoDTO");
        return userInfoDTO.getSub();
    }
}