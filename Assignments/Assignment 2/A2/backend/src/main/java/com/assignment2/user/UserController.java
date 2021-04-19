package com.assignment2.user;

import static com.assignment2.UrlMapping.*;
import com.assignment2.user.dto.UserDTO;
import com.assignment2.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }

    @PatchMapping
    public UserDTO update(@RequestBody UserDTO user) {
        return userService.update(user);
    }

    @DeleteMapping(DELETE_ENTITY)
    public void delete(@PathVariable Long id) { userService.delete(id); }
}
