package com.findsoulmate.config;

import com.findsoulmate.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationAgainstThirdPartySystem {

    private final UserDetailsServiceImpl servicesUsers;

    public boolean shouldAuth(String name, String password) {
        boolean hasAuth = servicesUsers.authByUserAndPassword(name, password);
        if (hasAuth) {
            log.info("Auth for user {}", name);
        }
        return hasAuth;
    }
}