package ru.ssau.tk.practiceoop1.db.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ssau.tk.practiceoop1.db.service.UserService;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        // Извлекаем JWT из запроса
        String jwt = parseJwt(request);

        // Если JWT существует и валиден, аутентифицируем пользователя
        if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
            String username = jwtTokenProvider.getUsernameFromToken(jwt);  // Получаем имя пользователя из токена
            UserDetails userDetails = userService.loadUserByUsername(username); // Загружаем данные пользователя из базы данных

            if (userDetails != null) {
                // Создаем объект аутентификации
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // Устанавливаем детали аутентификации
                SecurityContextHolder.getContext().setAuthentication(authentication);  // Устанавливаем аутентификацию в контекст
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Если пользователь не найден
                return;
            }
        }

        // Пропускаем запрос дальше в цепочке фильтров
        filterChain.doFilter(request, response);
    }

    // Извлекаем JWT из заголовка Authorization
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);  // Извлекаем токен из заголовка
        }
        return null;
    }
}
