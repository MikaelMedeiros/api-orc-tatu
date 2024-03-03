package com.codejokers.orctatu.dto;

public record UserResponseDTO(String name,
                              String picture,
                              String accessToken,
                              String tokenType,

                              Long expiration,
                              String refreshToken) {}