package com.cicd.myapi.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;

public class MemberUserDetail extends User {

    private String email;
    private String password;
    private String nickname;
    private boolean social;
    private List<String> roleNames = new ArrayList<>();

    public MemberUserDetail(String email, String password, String nickname, boolean social, List<String> roleNames) {
        super(email, password, roleNames.stream()
                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                .collect(Collectors.toList()));
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.social = social;
        this.roleNames = roleNames;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("nickname", nickname);
        map.put("social", social);
        map.put("roleNames", roleNames);
        return map;
    }
}
