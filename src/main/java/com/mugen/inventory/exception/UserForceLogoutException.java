package com.mugen.inventory.exception;

import org.springframework.security.core.AuthenticationException;

public class UserForceLogoutException extends AuthenticationException {
    public UserForceLogoutException(String message) {
        super(message);
    }
}

