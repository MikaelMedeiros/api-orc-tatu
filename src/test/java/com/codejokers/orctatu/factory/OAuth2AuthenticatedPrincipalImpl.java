package com.codejokers.orctatu.factory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OAuth2AuthenticatedPrincipalImpl implements OAuth2AuthenticatedPrincipal {

    @Override
    public <A> A getAttribute(final String name) {
        return OAuth2AuthenticatedPrincipal.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        final Map<String, Object> attributes = new HashMap<>(){};
        final Object googleIdObject = "12345";
        attributes.put("sub", googleIdObject);
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}