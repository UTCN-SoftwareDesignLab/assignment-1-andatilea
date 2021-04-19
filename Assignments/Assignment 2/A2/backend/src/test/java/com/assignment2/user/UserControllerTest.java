package com.assignment2.user;
import com.assignment2.BaseControllerTest;
import com.assignment2.TestCreationFactory;
import com.assignment2.UrlMapping;
import com.assignment2.user.dto.UserDTO;
import com.assignment2.user.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder encoder;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }


    @Test
    void create() throws Exception {

        UserDTO reqUser = UserDTO.builder()
                .username(TestCreationFactory.randomString())
                .password(encoder.encode(TestCreationFactory.randomString()))
                .email(TestCreationFactory.randomString())
                .build();

        when(userService.create(reqUser)).thenReturn(reqUser);

        ResultActions result = performPostWithRequestBody(UrlMapping.USER, reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void update() throws Exception {

        UserDTO reqUser = UserDTO.builder()
                .username(TestCreationFactory.randomString())
                .password(encoder.encode(TestCreationFactory.randomString()))
                .email(TestCreationFactory.randomString())
                .build();

        when(userService.update(reqUser)).thenReturn(reqUser);

        ResultActions result = performPatchWithRequestBody(UrlMapping.USER, reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void delete() throws Exception {
                UserDTO reqUser = UserDTO.builder()
                .id(TestCreationFactory.randomLong())
                .username(TestCreationFactory.randomString())
                .password(encoder.encode(TestCreationFactory.randomString()))
                .email(TestCreationFactory.randomString())
                .build();
        ResultActions result = performDeleteWithPathVariable(UrlMapping.USER+UrlMapping.DELETE_ENTITY,reqUser.getId().toString());
        result.andExpect(status().isOk());
    }
}