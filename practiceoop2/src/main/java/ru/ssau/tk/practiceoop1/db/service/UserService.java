package ru.ssau.tk.practiceoop1.db.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder; // Используйте интерфейс PasswordEncoder
import org.springframework.stereotype.Service;
import ru.ssau.tk.practiceoop1.db.model.UserEntity;
import ru.ssau.tk.practiceoop1.db.repositories.UserRepository;
import ru.ssau.tk.practiceoop1.db.security.CustomUserDetails;
import ru.ssau.tk.practiceoop1.exceptions.UserNotFoundException;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Инжектируем интерфейс PasswordEncoder

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Spring инжектирует бин PasswordEncoder
    }

    @Transactional
    public UserEntity register(UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифруем пароль
        return userRepository.save(user); // Сохраняем в базе данных
    }

    @Transactional
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username)); // Выбрасываем исключение, если пользователь не найден
    }

    @Transactional
    public void delete(String username) {
        userRepository.deleteByUsername(username); // Удаляем из базы
    }

    // Загружаем пользователя для аутентификации
    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        UserEntity user = findByUsername(username);
        return CustomUserDetails.build(user); // Создаем CustomUserDetails для аутентификации
    }
}
