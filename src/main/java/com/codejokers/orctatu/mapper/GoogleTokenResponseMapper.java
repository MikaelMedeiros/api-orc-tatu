package com.codejokers.orctatu.mapper;

import com.codejokers.orctatu.dto.RefreshTokenInfoDTO;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class GoogleTokenResponseMapper {

    public abstract RefreshTokenInfoDTO toRefreshTokenInfoDTO(final GoogleTokenResponse googleTokenResponse);
}