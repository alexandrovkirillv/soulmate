package com.findsoulmate.controllers;

import com.findsoulmate.models.RegistrationForm;
import com.findsoulmate.models.Customer;
import com.findsoulmate.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/registration")
public class RegController {

    private final CustomerRepository customerRepository;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String processUser(RegistrationForm registrationForm, Model model) {
        Customer customer = customerRepository.findByUsername(registrationForm.getUsername());
        if (customer != null) {
            model.addAttribute("user", customer.getUsername() + ", already exist!");
            return "registration";
        }
        customerRepository.save(registrationForm.toUser());
        log.info("Registered: {}", registrationForm.getUsername());
        return "redirect:/login";
    }

}