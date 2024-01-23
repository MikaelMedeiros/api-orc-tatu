package com.codejokers.orctatu.dto;

public record TokenInfoDTO(
        String sub,
        String name,
        String email,
        String picture,
        String exp,
        String iat
) {
}
