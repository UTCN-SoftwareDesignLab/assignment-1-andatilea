package com.assignment2.security;

import com.assignment2.user.RoleRepository;
import com.assignment2.user.UserRepository;
import com.assignment2.user.model.ERole;
import com.assignment2.user.model.Role;
import com.assignment2.user.model.User;
import com.assignment2.security.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(SignupRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .build();

        Set<String> rolesStr = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.REGULAR_USER)
                    .orElseThrow(() -> new RuntimeException("Cannot find REGULAR_USER role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
    }
}
