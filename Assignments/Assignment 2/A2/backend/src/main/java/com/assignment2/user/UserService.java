package com.assignment2.user;

import com.assignment2.user.dto.UserDTO;
import com.assignment2.user.mapper.UserMapper;
import com.assignment2.user.dto.UserListDTO;
import com.assignment2.user.dto.UserMinimalDTO;
import com.assignment2.user.model.ERole;
import com.assignment2.user.model.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.assignment2.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    private User setRoleIDs(User user) {
        Set<Role> roleSet = new HashSet<>();
        user.getRoles()
                .forEach(role -> {
                    Role newRole = roleRepository.findByName(role.getName()).orElseThrow(() -> new RuntimeException("Unable to find role"));
                    roleSet.add(newRole);
                });
        user.setRoles(roleSet);
        return user;
    }

    // using the Registration idea from the Authentication Service we create a new user
    public UserDTO create(UserDTO user){

        User newUser = User.builder()
                .username(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .email(user.getEmail())
                .build();

        Set<Role> roles = new HashSet<>();

        if (user.getRoles() == null) {
            Role defaultRole = roleRepository.findByName(ERole.REGULAR_USER)
                    .orElseThrow(() -> new RuntimeException("Cannot find REGULAR_USER role"));
            roles.add(defaultRole);
        } else {
            user.getRoles().forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        newUser.setRoles(roles);
        userRepository.save(newUser);
        
        return user;
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    // we will need to update all fields of the wanted user
    public UserDTO update(UserDTO user) {

        User actUser = findById(user.getId());
        actUser.setId(user.getId());
        actUser.setUsername(user.getUsername());
        actUser.setPassword(encoder.encode(user.getPassword()));
        actUser.setEmail(user.getEmail());
        userRepository.save(actUser);
        System.out.println("User update done!");
        return user;
        }

    public void delete(Long id) {
        User actUser = findById(id);
        userRepository.delete(actUser);
        System.out.println("User deletion successful!");
    }
}
