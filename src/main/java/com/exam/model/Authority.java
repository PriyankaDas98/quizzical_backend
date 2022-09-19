package com.exam.model;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private String auth;
    public Authority(String authority){
        this.auth =authority;
    }
    @Override
    public String getAuthority() {
        return this.auth;
    }
}
