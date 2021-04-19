package com.assignment2.user.mapper;

import com.assignment2.books.model.Book;
import com.assignment2.books.model.dto.BookDTO;
import com.assignment2.user.dto.UserDTO;
import com.assignment2.user.dto.UserListDTO;
import com.assignment2.user.dto.UserMinimalDTO;
import com.assignment2.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }

}
