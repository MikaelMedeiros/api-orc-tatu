package com.codejokers.orctatu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RefreshTokenInfoDTO {

    @JsonIgnore
    private String idToken;
    private String accessToken;
    private String tokenType;
    private String expiresInSeconds;
    @JsonIgnore
    private String scope;
}