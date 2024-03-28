package com.findsoulmate.models;

import lombok.Data;

import java.util.Collections;

@Data
public class RegistrationForm {

    private String username;
    private String password;

    public Customer toUser() {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        return customer;
    }
}
