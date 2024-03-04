package com.codejokers.orctatu.dto;

public record RefreshTokenDTO(String accessToken,
                              String tokenType,
                              Long expiresInSeconds) {}