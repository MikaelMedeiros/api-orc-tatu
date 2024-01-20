package com.codejokers.orctatu.dtos;

import org.apache.http.annotation.Contract;

public record UserInfo(
        String sub,
        String name,
        String given_name,
        String family_name,
        String picture,
        String email,
        boolean email_verified,
        String locale,
        String token
) {

    public UserInfo(String sub, String name, String image, String token) {
        this(sub,name,null,null,image,null,true,null,token);
    }
}
