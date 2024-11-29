package ru.ssau.tk.practiceoop1.db.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.ssau.tk.practiceoop1.db.security.JwtFilter;
import ru.ssau.tk.practiceoop1.db.security.JwtTokenProvider;
import ru.ssau.tk.practiceoop1.db.service.UserService;

@Configuration
public class SecurityConfig{

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoderConfig passwordEncoderConfig) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public JwtFilter jwtAuthTokenFilter() {
        return new JwtFilter(jwtTokenProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll() // Разрешаем доступ к этим эндпоинтам без авторизации
                        .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll() // Публичные GET запросы
                        .requestMatchers(HttpMethod.POST, "/api/private/**").authenticated() // Только авторизованные могут POST
                        .anyRequest().authenticated() // Все остальные запросы требуют авторизации
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Отключаем сессии
                .addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class) // Добавляем фильтр для JWT
                .authenticationProvider(authenticationProvider()) // Используем наш AuthenticationProvider
                .build();
    }

    // AuthenticationProvider для UserService
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); // Используем UserService как источник данных
        authProvider.setPasswordEncoder(passwordEncoderConfig.passwordEncoder()); // Используем BCrypt для проверки паролей
        return authProvider;
    }
}
