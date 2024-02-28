package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.CalculationSettingsDTO;
import com.codejokers.orctatu.dto.UserDTO;
import com.codejokers.orctatu.entity.CalculationSettings;
import com.codejokers.orctatu.entity.User;
import com.codejokers.orctatu.exception.ApplicationException;
import com.codejokers.orctatu.mapper.CalculationSettingsMapper;
import com.codejokers.orctatu.repository.CalculationSettingsRepository;
import com.codejokers.orctatu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculationSettingsService {

    private final CalculationSettingsMapper calculationSettingsMapper;
    private final CalculationSettingsRepository calculationSettingsRepository;
    private final UserRepository userRepository;

    public CalculationSettingsDTO find(final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final CalculationSettings calculationSettings = getCalculationSettings(oAuth2AuthenticatedPrincipal);
        return calculationSettingsMapper.toCalculationSettingsDTO(calculationSettings);
    }

    public CalculationSettingsDTO save(final CalculationSettingsDTO calculationSettingsDTO, final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final User user = getUser(oAuth2AuthenticatedPrincipal);
        final CalculationSettings calculationSettings = calculationSettingsMapper.toCalculationSettings(calculationSettingsDTO);
        calculationSettings.setUser(user);
        final CalculationSettings calculationSettingsSaved = calculationSettingsRepository.save(calculationSettings);
        return calculationSettingsMapper.toCalculationSettingsDTO(calculationSettingsSaved);
    }

    public CalculationSettingsDTO update(final CalculationSettingsDTO calculationSettingsDTO, final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final CalculationSettings calculationSettings = getCalculationSettings(oAuth2AuthenticatedPrincipal);
        setFields(calculationSettings, calculationSettingsDTO);
        final CalculationSettings calculationSettingsUpdated = calculationSettingsRepository.save(calculationSettings);
        return calculationSettingsMapper.toCalculationSettingsDTO(calculationSettingsUpdated);
    }

    private CalculationSettings getCalculationSettings(final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final User user = getUser(oAuth2AuthenticatedPrincipal);
        return calculationSettingsRepository.findById(user.getId())
                                            .orElseThrow(() -> new ApplicationException(404, "Não existe uma configuração de cálculos para esse usuário."));
    }

    private User getUser(final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final UserDTO userDTO = (UserDTO) oAuth2AuthenticatedPrincipal.getAttributes().get("userDTO");
        return userRepository.findByGoogleId(userDTO.getSub()).orElseThrow(() -> new ApplicationException(404, "Usuário não encontrado."));
    }

    private void setFields(final CalculationSettings calculationSettings, final CalculationSettingsDTO calculationSettingsDTO) {
        calculationSettings.setPricePerCentimeter(calculationSettingsDTO.pricePerCentimeter());
        calculationSettings.setStudioPercentage(calculationSettingsDTO.studioPercentage());
        calculationSettings.setParkingCost(calculationSettingsDTO.parkingCost());
        calculationSettings.setMaterialCost(calculationSettingsDTO.materialCost());
        calculationSettings.setCreditCardFee(calculationSettingsDTO.creditCardFee());
    }
}