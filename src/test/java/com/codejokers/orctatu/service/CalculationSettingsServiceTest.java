package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.CalculationSettingsDTO;
import com.codejokers.orctatu.dto.UserInfoDTO;
import com.codejokers.orctatu.entity.CalculationSettings;
import com.codejokers.orctatu.exception.ApplicationException;
import com.codejokers.orctatu.factory.CalculationSettingsDTOFactory;
import com.codejokers.orctatu.factory.CalculationSettingsFactory;
import com.codejokers.orctatu.factory.OAuth2AuthenticatedPrincipalImpl;
import com.codejokers.orctatu.mapper.CalculationSettingsMapper;
import com.codejokers.orctatu.repository.CalculationSettingsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CalculationSettingsServiceTest {

    @InjectMocks
    private CalculationSettingsService calculationSettingsService;
    @Mock
    private CalculationSettingsMapper calculationSettingsMapper;
    @Mock
    private CalculationSettingsRepository calculationSettingsRepository;
    private final OAuth2AuthenticatedPrincipalImpl oAuth2AuthenticatedPrincipalImpl = new OAuth2AuthenticatedPrincipalImpl();

    @Test
    void givenFind_whenEntityWithTheInformedIdExist_thenReturnCalculationSettings() throws JsonProcessingException {

        final CalculationSettings expectedCalculationSettings = CalculationSettingsFactory.createCalculationSettings();
        when(calculationSettingsRepository.findById(any(String.class))).thenReturn(Optional.of(expectedCalculationSettings));
        final CalculationSettings calculationSettings = calculationSettingsService.find(oAuth2AuthenticatedPrincipalImpl);

        verify(calculationSettingsRepository).findById(getSub());
        assertEquals(expectedCalculationSettings.getId(), calculationSettings.getId());
        assertEquals(expectedCalculationSettings.getPricePerCentimeter(), calculationSettings.getPricePerCentimeter());
        assertEquals(expectedCalculationSettings.getStudioPercentage(), calculationSettings.getStudioPercentage());
        assertEquals(expectedCalculationSettings.getParkingCost(), calculationSettings.getParkingCost());
        assertEquals(expectedCalculationSettings.getMaterialCost(), calculationSettings.getMaterialCost());
        assertEquals(expectedCalculationSettings.getCreditCardFee(), calculationSettings.getCreditCardFee());
    }

    @Test
    void givenFind_whenEntityWithTheInformedIdDoNotExist_thenThrowApplicationException() {

        when(calculationSettingsRepository.findById(any(String.class))).thenReturn(Optional.empty());
        final ApplicationException applicationException = assertThrowsExactly(ApplicationException.class, () -> calculationSettingsService.find(oAuth2AuthenticatedPrincipalImpl));

        verify(calculationSettingsRepository).findById(getSub());
        assertEquals(404, applicationException.getStatus());
        assertEquals("Não existe uma configuração de cálculos para esse usuário.", applicationException.getMessage());
    }

    @Test
    void givenSave_whenInputDataIsCorrect_thenSaveAndReturnCalculationSettings() throws JsonProcessingException {

        final CalculationSettingsDTO calculationSettingsDTO = CalculationSettingsDTOFactory.createCalculationSettingsDTO();
        final CalculationSettings expectedCalculationSettings = CalculationSettingsFactory.createCalculationSettings();
        when(calculationSettingsMapper.toCalculationSettings(any(CalculationSettingsDTO.class))).thenReturn(expectedCalculationSettings);
        when(calculationSettingsRepository.save(any(CalculationSettings.class))).thenReturn(expectedCalculationSettings);
        final CalculationSettings calculationSettingsSaved = calculationSettingsService.save(calculationSettingsDTO, oAuth2AuthenticatedPrincipalImpl);

        verify(calculationSettingsMapper).toCalculationSettings(calculationSettingsDTO);
        verify(calculationSettingsRepository).save(expectedCalculationSettings);
        assertEquals(expectedCalculationSettings.getId(), calculationSettingsSaved.getId());
        assertEquals(expectedCalculationSettings.getPricePerCentimeter(), calculationSettingsSaved.getPricePerCentimeter());
        assertEquals(expectedCalculationSettings.getStudioPercentage(), calculationSettingsSaved.getStudioPercentage());
        assertEquals(expectedCalculationSettings.getParkingCost(), calculationSettingsSaved.getParkingCost());
        assertEquals(expectedCalculationSettings.getMaterialCost(), calculationSettingsSaved.getMaterialCost());
        assertEquals(expectedCalculationSettings.getCreditCardFee(), calculationSettingsSaved.getCreditCardFee());
    }

    @Test
    void givenUpdate_whenInputDataIsCorrect_thenUpdateAndReturnCalculationSettings() throws JsonProcessingException {

        final CalculationSettingsDTO calculationSettingsDTO = CalculationSettingsDTOFactory.createCalculationSettingsDTO();
        final CalculationSettings expectedCalculationSettings = CalculationSettingsFactory.createCalculationSettings();
        when(calculationSettingsRepository.findById(any(String.class))).thenReturn(Optional.of(expectedCalculationSettings));
        when(calculationSettingsRepository.save(any(CalculationSettings.class))).thenReturn(expectedCalculationSettings);
        final CalculationSettings calculationSettingsUpdated = calculationSettingsService.update(calculationSettingsDTO, oAuth2AuthenticatedPrincipalImpl);

        verify(calculationSettingsRepository).findById(getSub());
        verify(calculationSettingsRepository).save(expectedCalculationSettings);
        assertEquals(expectedCalculationSettings.getId(), calculationSettingsUpdated.getId());
        assertEquals(expectedCalculationSettings.getPricePerCentimeter(), calculationSettingsUpdated.getPricePerCentimeter());
        assertEquals(expectedCalculationSettings.getStudioPercentage(), calculationSettingsUpdated.getStudioPercentage());
        assertEquals(expectedCalculationSettings.getParkingCost(), calculationSettingsUpdated.getParkingCost());
        assertEquals(expectedCalculationSettings.getMaterialCost(), calculationSettingsUpdated.getMaterialCost());
        assertEquals(expectedCalculationSettings.getCreditCardFee(), calculationSettingsUpdated.getCreditCardFee());
    }

    @Test
    void givenUpdate_whenEntityWithTheInformedIdDoNotExist_thenThrowApplicationException() throws JsonProcessingException {

        final CalculationSettingsDTO calculationSettingsDTO = CalculationSettingsDTOFactory.createCalculationSettingsDTO();
        when(calculationSettingsRepository.findById(any(String.class))).thenReturn(Optional.empty());
        final ApplicationException applicationException = assertThrowsExactly(ApplicationException.class, () -> calculationSettingsService.update(calculationSettingsDTO, oAuth2AuthenticatedPrincipalImpl));

        verify(calculationSettingsRepository).findById(getSub());
        assertEquals(404, applicationException.getStatus());
        assertEquals("Não existe uma configuração de cálculos para esse usuário.", applicationException.getMessage());
    }

    private String getSub() {
        final UserInfoDTO userInfoDTO = (UserInfoDTO) oAuth2AuthenticatedPrincipalImpl.getAttributes().get("userInfoDTO");
        return userInfoDTO.getSub();
    }
}