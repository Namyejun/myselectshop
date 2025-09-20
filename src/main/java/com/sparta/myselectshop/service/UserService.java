package com.sparta.myselectshop.service;

import com.sparta.myselectshop.entity.SignupRequestDto;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String email = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 중복 확인 시퀀스
        Optional<User> user1 = userRepository.findByUsername(username);
        if (user1.isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }

        Optional<User> user2 = userRepository.findByEmail(email);
        if (user2.isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        UserRoleEnum userRole = UserRoleEnum.USER;

        if (requestDto.isAdmin()) {
            if (requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                userRole = UserRoleEnum.ADMIN;
            } else {
                throw new IllegalArgumentException("Admin token does not match");
            }
        }

        User user = new User(username, password, email, userRole);
        userRepository.save(user);
    }

}
