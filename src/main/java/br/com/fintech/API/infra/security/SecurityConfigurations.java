package br.com.fintech.API.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.GET, "/accounts").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.PUT, "/accounts").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.POST, "/accounts").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.DELETE, "/accounts").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.GET, "/assets").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.GET, "/courses").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.PUT, "/courses").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.POST, "/courses").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.DELETE, "/courses").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.GET, "/lessons").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.PUT, "/lessons").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.POST, "/lessons").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.DELETE, "/lessons").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.GET, "/investments").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.PUT, "/investments").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.POST, "/investments").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.DELETE, "/investments").hasAuthority("AUTHORIZED")
                        .requestMatchers(HttpMethod.PUT, "/accounts/{accountId}/courses/{courseId}/lessons/{lessonId}/progress").hasAuthority("AUTHORIZED")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
