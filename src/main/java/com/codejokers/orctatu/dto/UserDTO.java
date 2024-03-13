package com.codejokers.orctatu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String sub;
    private String name;
    private String picture;
    private String email;
    private Boolean email_verified;
    private String locale;
    private String authorities;
    private String accessToken;
    private String tokenType;
    private Long expiration;
    private String refreshToken;
}