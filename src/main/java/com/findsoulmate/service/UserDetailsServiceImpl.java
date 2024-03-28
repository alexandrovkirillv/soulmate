package com.findsoulmate.service;

import com.findsoulmate.models.Customer;
import com.findsoulmate.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByUsername(username);
    }

    public boolean authByUserAndPassword(String userFromRequest, String password) {
        Customer customer = customerRepository.findByUsername(userFromRequest);
        if (customer == null) {
            log.info("User doesn't exist");
            return false;
        } else {
            return customer.getPassword().equals(password);
        }
    }
}
