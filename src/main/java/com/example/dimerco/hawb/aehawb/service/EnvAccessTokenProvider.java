package com.example.dimerco.hawb.aehawb.service;


import org.springframework.stereotype.Component;

import com.example.dimerco.hawb.aehawb.service.impl.AccessTokenProviderImpl;

@Component
public class EnvAccessTokenProvider implements AccessTokenProviderImpl {

    @Override
    public String getAccessToken() {
        return "mock-token-if-needed";
    }
}