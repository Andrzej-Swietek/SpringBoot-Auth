package pl.swietek.springbootapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import pl.swietek.springbootapi.configuration.JWTAuthFilter;

import static org.springframework.http.HttpMethod.*;
import static pl.swietek.springbootapi.models.Permission.*;
import static pl.swietek.springbootapi.models.Role.ADMIN;
import static pl.swietek.springbootapi.models.Role.USER;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final JWTAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/auth/**",
                        "/api/v1/public/**"
                ).permitAll()

                .requestMatchers( GET, "/api/v1/user/**").hasAnyAuthority(ADMIN_READ.name(), MASTER_READ.name(), USER.name())
                .requestMatchers( POST,"/api/v1/user/**").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers( PUT,"/api/v1/user/**").hasAnyAuthority(ADMIN_UPDATE.name())
                .requestMatchers( DELETE,"/api/v1/user/**").hasAnyAuthority(ADMIN_DELETE.name())

                 /* .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name()) */
                .requestMatchers("/api/auth/logout").authenticated()
                .anyRequest().authenticated()
                .and()
                    .cors()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout().logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
        return http.build();
    }
}
