package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.CalculationSettingsDTO;
import com.codejokers.orctatu.dto.UserDTO;
import com.codejokers.orctatu.entity.CalculationSettings;
import com.codejokers.orctatu.entity.User;
import com.codejokers.orctatu.exception.ApplicationException;
import com.codejokers.orctatu.factory.CalculationSettingsDTOFactory;
import com.codejokers.orctatu.factory.CalculationSettingsFactory;
import com.codejokers.orctatu.factory.OAuth2AuthenticatedPrincipalImpl;
import com.codejokers.orctatu.factory.UserFactory;
import com.codejokers.orctatu.mapper.CalculationSettingsMapper;
import com.codejokers.orctatu.repository.CalculationSettingsRepository;
import com.codejokers.orctatu.repository.UserRepository;
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
    @Mock
    private UserRepository userRepository;
    private final OAuth2AuthenticatedPrincipalImpl oAuth2AuthenticatedPrincipalImpl = new OAuth2AuthenticatedPrincipalImpl();

    @Test
    void givenFind_whenEntityWithTheInformedIdExist_thenReturnCalculationSettings() throws JsonProcessingException {

        final User user = UserFactory.createUser();
        final CalculationSettingsDTO calculationSettingsDTO = CalculationSettingsDTOFactory.createCalculationSettingsDTO();
        final CalculationSettings expectedCalculationSettings = CalculationSettingsFactory.createCalculationSettings();
        when(userRepository.findByGoogleId(any(String.class))).thenReturn(Optional.of(user));
        when(calculationSettingsRepository.findById(user.getId())).thenReturn(Optional.of(expectedCalculationSettings));
        when(calculationSettingsMapper.toCalculationSettingsDTO(any(CalculationSettings.class))).thenReturn(calculationSettingsDTO);
        final CalculationSettingsDTO calculationSettingsDTOResponse = calculationSettingsService.find(oAuth2AuthenticatedPrincipalImpl);

        verify(userRepository).findByGoogleId(getSub());
        verify(calculationSettingsRepository).findById(user.getId());
        assertEquals(expectedCalculationSettings.getPricePerCentimeter(), calculationSettingsDTOResponse.pricePerCentimeter());
        assertEquals(expectedCalculationSettings.getStudioPercentage(), calculationSettingsDTOResponse.studioPercentage());
        assertEquals(expectedCalculationSettings.getParkingCost(), calculationSettingsDTOResponse.parkingCost());
        assertEquals(expectedCalculationSettings.getMaterialCost(), calculationSettingsDTOResponse.materialCost());
        assertEquals(expectedCalculationSettings.getCreditCardFee(), calculationSettingsDTOResponse.creditCardFee());
    }

    @Test
    void givenFind_whenUserDoNotExist_thenThrowApplicationException() {

        when(userRepository.findByGoogleId(any(String.class))).thenReturn(Optional.empty());
        final ApplicationException applicationException = assertThrowsExactly(ApplicationException.class, () -> calculationSettingsService.find(oAuth2AuthenticatedPrincipalImpl));

        verify(userRepository).findByGoogleId(getSub());
        assertEquals(404, applicationException.getStatus());
        assertEquals("Usuário não encontrado.", applicationException.getMessage());
    }

    @Test
    void givenFind_whenEntityWithTheInformedIdDoNotExist_thenThrowApplicationException() throws JsonProcessingException {

        final User user = UserFactory.createUser();
        when(userRepository.findByGoogleId(any(String.class))).thenReturn(Optional.of(user));
        when(calculationSettingsRepository.findById(user.getId())).thenReturn(Optional.empty());
        final ApplicationException applicationException = assertThrowsExactly(ApplicationException.class, () -> calculationSettingsService.find(oAuth2AuthenticatedPrincipalImpl));

        verify(userRepository).findByGoogleId(getSub());
        verify(calculationSettingsRepository).findById(user.getId());
        assertEquals(404, applicationException.getStatus());
        assertEquals("Não existe uma configuração de cálculos para esse usuário.", applicationException.getMessage());
    }

    @Test
    void givenSave_whenInputDataIsCorrect_thenSaveAndReturnCalculationSettings() throws JsonProcessingException {

        final User user = UserFactory.createUser();
        final CalculationSettingsDTO calculationSettingsDTO = CalculationSettingsDTOFactory.createCalculationSettingsDTO();
        final CalculationSettings expectedCalculationSettings = CalculationSettingsFactory.createCalculationSettings();
        when(userRepository.findByGoogleId(any(String.class))).thenReturn(Optional.of(user));
        when(calculationSettingsMapper.toCalculationSettings(any(CalculationSettingsDTO.class))).thenReturn(expectedCalculationSettings);
        when(calculationSettingsRepository.save(any(CalculationSettings.class))).thenReturn(expectedCalculationSettings);
        when(calculationSettingsMapper.toCalculationSettingsDTO(any(CalculationSettings.class))).thenReturn(calculationSettingsDTO);
        final CalculationSettingsDTO calculationSettingsSaved = calculationSettingsService.save(calculationSettingsDTO, oAuth2AuthenticatedPrincipalImpl);

        verify(userRepository).findByGoogleId(getSub());
        verify(calculationSettingsMapper).toCalculationSettings(calculationSettingsDTO);
        verify(calculationSettingsRepository).save(expectedCalculationSettings);
        verify(calculationSettingsMapper).toCalculationSettingsDTO(expectedCalculationSettings);
        assertEquals(expectedCalculationSettings.getPricePerCentimeter(), calculationSettingsSaved.pricePerCentimeter());
        assertEquals(expectedCalculationSettings.getStudioPercentage(), calculationSettingsSaved.studioPercentage());
        assertEquals(expectedCalculationSettings.getParkingCost(), calculationSettingsSaved.parkingCost());
        assertEquals(expectedCalculationSettings.getMaterialCost(), calculationSettingsSaved.materialCost());
        assertEquals(expectedCalculationSettings.getCreditCardFee(), calculationSettingsSaved.creditCardFee());
    }

    @Test
    void givenUpdate_whenInputDataIsCorrect_thenUpdateAndReturnCalculationSettings() throws JsonProcessingException {

        final User user = UserFactory.createUser();
        final CalculationSettingsDTO calculationSettingsDTO = CalculationSettingsDTOFactory.createCalculationSettingsDTO();
        final CalculationSettings expectedCalculationSettings = CalculationSettingsFactory.createCalculationSettings();
        when(userRepository.findByGoogleId(any(String.class))).thenReturn(Optional.of(user));
        when(calculationSettingsRepository.findById(user.getId())).thenReturn(Optional.of(expectedCalculationSettings));
        when(calculationSettingsRepository.save(any(CalculationSettings.class))).thenReturn(expectedCalculationSettings);
        when(calculationSettingsMapper.toCalculationSettingsDTO(any(CalculationSettings.class))).thenReturn(calculationSettingsDTO);
        final CalculationSettingsDTO calculationSettingsUpdated = calculationSettingsService.update(calculationSettingsDTO, oAuth2AuthenticatedPrincipalImpl);

        verify(userRepository).findByGoogleId(getSub());
        verify(calculationSettingsRepository).findById(user.getId());
        verify(calculationSettingsRepository).save(expectedCalculationSettings);
        verify(calculationSettingsMapper).toCalculationSettingsDTO(expectedCalculationSettings);
        assertEquals(expectedCalculationSettings.getPricePerCentimeter(), calculationSettingsUpdated.pricePerCentimeter());
        assertEquals(expectedCalculationSettings.getStudioPercentage(), calculationSettingsUpdated.studioPercentage());
        assertEquals(expectedCalculationSettings.getParkingCost(), calculationSettingsUpdated.parkingCost());
        assertEquals(expectedCalculationSettings.getMaterialCost(), calculationSettingsUpdated.materialCost());
        assertEquals(expectedCalculationSettings.getCreditCardFee(), calculationSettingsUpdated.creditCardFee());
    }

    private String getSub() {
        final UserDTO userDTO = (UserDTO) oAuth2AuthenticatedPrincipalImpl.getAttributes().get("userDTO");
        return userDTO.getSub();
    }
}