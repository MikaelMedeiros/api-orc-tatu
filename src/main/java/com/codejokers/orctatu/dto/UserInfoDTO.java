package com.codejokers.orctatu.dto;

public record UserInfoDTO(String sub,
                          String name,
                          String given_name,
                          String family_name,
                          String picture,
                          String email,
                          boolean email_verified,
                          String locale,
                          String token,
                          String expiration) {

    public UserInfoDTO(final String sub, final String name, final String image, final String token, final String expiration) {
        this(sub, name, null, null, image, null, true, null, token, expiration);
    }
}