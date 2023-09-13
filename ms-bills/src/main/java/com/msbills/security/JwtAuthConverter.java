package com.msbills.security;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class JwtAuthConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println(source.getClaims());
        Map<String, Object> realAccessToken = (Map<String, Object>) source.getClaims().get("realm_access");
        List<String> groups = (List<String>) source.getClaims().get("user_groups");

        if (realAccessToken != null || !realAccessToken.isEmpty()) {
            authorities.addAll(extractRole(realAccessToken));
        }
        if (groups != null || !groups.isEmpty()) {
            authorities.addAll(extractGroup(groups));
        }
        System.out.println(authorities);
        return authorities;
    }


    private Collection<GrantedAuthority> extractRole(Map<String, Object> realAccessToken) {
        return ((List<String>) realAccessToken.get("roles"))
                .stream().map(rol -> "ROLE_" + rol)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Collection<GrantedAuthority> extractGroup(List<String> groups) {
        return groups.stream().map(rol -> "ROLE_" + rol)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
