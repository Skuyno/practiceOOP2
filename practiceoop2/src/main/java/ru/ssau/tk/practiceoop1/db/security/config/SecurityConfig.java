package ru.ssau.tk.practiceoop1.db.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import ru.ssau.tk.practiceoop1.db.security.JwtFilter;
import ru.ssau.tk.practiceoop1.db.service.UserService;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Используем BCrypt для шифрования паролей
    }

    @Bean
    public JwtFilter jwtAuthTokenFilter() {
        return new JwtFilter();  // Создаем фильтр с зависимостями для обработки JWT
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(@Autowired UserService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();  // Менеджер аутентификации
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Разрешённые источники
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        
        // Разрешённые HTTP методы
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        
        // Разрешённые заголовки
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        // Разрешить учётные данные (например, куки)
        configuration.setAllowCredentials(true);
        
        // Применение к URL паттернам
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)  // Отключаем CSRF, потому что не используем сессии
                .cors()
                .and()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/auth/**").permitAll()  // Открытый доступ для аутентификации (например, login)
                                .requestMatchers(HttpMethod.GET, "/api/functions/**", "/api/points/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/functions/**", "/api/points/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/api/functions/**", "/api/points/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/functions/**", "/api/points/**").authenticated()
                                .anyRequest().authenticated()  // Все остальные запросы требуют авторизации
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Отключаем сессии, используем stateless
                .authenticationProvider(authenticationProvider(null))
                .addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class)  // Добавляем фильтр для JWT
                .build();
    }
}
