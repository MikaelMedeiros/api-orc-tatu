package com.codejokers.orctatu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenInfoDTO {

    @JsonIgnore
    private String idToken;
    private String accessToken;
    private String tokenType;
    @JsonIgnore
    private String refreshToken;
    private String expiration;
    private String expiresInSeconds;
    @JsonIgnore
    private String scope;
    @JsonIgnore
    private String accessType;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(final String idToken) {
        this.idToken = idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @JsonProperty("expiration")
    public String getExpiration() {
        return expiration;
    }

    @JsonProperty("exp")
    public void setExpiration(final String expiration) {
        this.expiration = expiration;
    }

    @JsonProperty("expiresInSeconds")
    public String getExpiresInSeconds() {
        return expiresInSeconds;
    }

    @JsonProperty("expires_in")
    public void setExpiresInSeconds(final String expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(final String scope) {
        this.scope = scope;
    }

    @JsonProperty("accessType")
    public String getAccessType() {
        return accessType;
    }

    @JsonProperty("access_type")
    public void setAccessType(final String accessType) {
        this.accessType = accessType;
    }

    @Override
    public String toString() {
        return "TokenInfoDTO{" +
                "idToken='" + idToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiration='" + expiration + '\'' +
                ", expiresInSeconds='" + expiresInSeconds + '\'' +
                ", scope='" + scope + '\'' +
                ", accessType='" + accessType + '\'' +
                '}';
    }
}