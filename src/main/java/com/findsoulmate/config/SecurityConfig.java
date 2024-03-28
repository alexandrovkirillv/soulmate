package com.findsoulmate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final AuthenticationAgainstThirdPartySystem thirdPartySystem;
    private final LoggingAccessDeniedHandler accessDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> {
                            try {
                                auth
                                        .anyRequest().authenticated()
                                        .and().formLogin()
                                        .loginPage("/login")
                                        .defaultSuccessUrl("/", true)
                                        .permitAll()
                                        .and()
                                        .logout()
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                        .logoutSuccessUrl("/login?logout")
                                        .permitAll()
                                        .and()
                                        .exceptionHandling()
                                        .accessDeniedHandler(accessDeniedHandler);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .authenticationManager(new CustomAuthenticationManager(thirdPartySystem));
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/registration", "static/**");
    }
}