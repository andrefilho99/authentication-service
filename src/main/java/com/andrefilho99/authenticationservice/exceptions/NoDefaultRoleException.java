package com.andrefilho99.authenticationservice.exceptions;

public class NoDefaultRoleException extends RuntimeException {
    public NoDefaultRoleException(String message) {
        super(message);
    }
}
