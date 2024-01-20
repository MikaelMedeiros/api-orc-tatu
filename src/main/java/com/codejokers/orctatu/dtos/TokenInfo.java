package com.codejokers.orctatu.dtos;

public record TokenInfo(
        String sub,
        String name,
        String email,
        String picture,
        String exp,
        String iat
) {
}
