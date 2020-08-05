package com.softserve.edu;

import com.softserve.edu.model.User;
import com.softserve.edu.repository.UserRepository;
import com.softserve.edu.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceImplTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void getAllTest() {
        User student1 = new User();
        User student2 = new User();

        student1.setEmail("student1@gmail.com");
        student1.setFirstName("fName1");
        student1.setLastName("lName1");
        student1.setRole(User.Role.TRAINEE);
        student1.setPassword("password1");
        student1.setProgresses(new ArrayList<>());

        student2.setEmail("student2@gmail.com");
        student2.setFirstName("fName2");
        student2.setLastName("lName2");
        student2.setRole(User.Role.TRAINEE);
        student2.setPassword("password2");
        student2.setProgresses(new ArrayList<>());

        List<User> expectedUsers = List.of(student1, student2);

        Mockito.when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.getAll();
        Assertions.assertEquals(expectedUsers, actualUsers);
    }


    @Test
    public void getUserByIdTest() {
        User expectedUser = new User();
        expectedUser.setFirstName("FName");
        expectedUser.setId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(expectedUser));
        User actualUser = userService.getUserById(1L);
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void deleteUerByIdTest() {
        User expectedUser = new User();
        expectedUser.setFirstName("FName");
        expectedUser.setId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(expectedUser));
        userService.deleteUserById(1L);
        Mockito.verify(userRepository).deleteById(Mockito.any());
    }

//    @Test
//    public void createOrUpdateUserTest() {
//        User user = new User();
//        user.setFirstName("FName");
//        user.setId(5L);
//
//        User expectedUser = new User();
//        expectedUser.setFirstName("UpdatedName");
//        expectedUser.setId(5L);
//        userService.createOrUpdateUser(expectedUser);
//
//        Mockito.when(userRepository.findById(5L)).thenReturn(java.util.Optional.of(user));
//        User actualUser = userService.getUserById(5L);
//
//        Assertions.assertEquals(expectedUser, actualUser);
//    }

}

