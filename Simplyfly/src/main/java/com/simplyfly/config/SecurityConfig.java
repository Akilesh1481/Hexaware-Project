package com.simplyfly.config;

import com.simplyfly.security.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize


                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/auth/login").permitAll()


                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api-docs/**",
                                "/v3/api-docs/**").permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/airports").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/airports").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/airports/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/airports/code/{code}").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/airports/search").permitAll()
                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/airports/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/airports/{id}").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/flights").hasAuthority("OWNER")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/flights").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/flights/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/flights/search").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/flights/owner/{ownerId}").hasAuthority("OWNER")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/flights/{id}").hasAuthority("OWNER")
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/v1/flights/{id}/status").hasAnyAuthority("OWNER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/flights/{id}").hasAnyAuthority("OWNER","ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/users/active").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/users/{id}").hasAnyAuthority("ADMIN","USER")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/users/search").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/users/{id}").hasAnyAuthority("ADMIN","USER")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/users/{id}").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/seats").hasAnyAuthority("OWNER","ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/seats/flight/{flightId}/generate/{totalSeats}")
                        .hasAnyAuthority("OWNER","ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/seats").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/seats/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/seats/flight/{flightId}").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/seats/flight/{flightId}/available").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/seats/flight/{flightId}/booked")
                        .hasAnyAuthority("OWNER","ADMIN")
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/v1/seats/{id}/status")
                        .hasAnyAuthority("OWNER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/seats/{id}").hasAnyAuthority("OWNER","ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/passengers").hasAuthority("USER")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/passengers/booking/{bookingId}")
                        .hasAnyAuthority("USER","ADMIN","OWNER")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/passengers/{id}").hasAnyAuthority("USER","ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/bookings").hasAuthority("USER")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/bookings").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/bookings/{id}")
                        .hasAnyAuthority("USER","ADMIN","OWNER")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/bookings/user/{userId}")
                        .hasAnyAuthority("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/bookings/flight/{flightId}")
                        .hasAnyAuthority("OWNER","ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/bookings/{id}/cancel")
                        .hasAnyAuthority("USER","ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/payments").hasAuthority("USER")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/payments/booking/{bookingId}")
                        .hasAnyAuthority("USER","ADMIN","OWNER")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/payments").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/refunds/{bookingId}").hasAuthority("OWNER")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/refunds/{bookingId}")
                        .hasAnyAuthority("USER","ADMIN","OWNER")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/refunds").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/refunds/{id}/process").hasAuthority("OWNER")

                        // ========== ADMIN ==========
                        .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao =
                new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailsService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}