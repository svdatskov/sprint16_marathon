package com.softserve.edu;

import com.softserve.edu.model.User;
import com.softserve.edu.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {
    public User student1 = new User();
    public User student2 = new User();
    public User mentor = new User();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void setUp() {
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

        mentor.setEmail("mentor@gmail.com");
        mentor.setFirstName("fName3");
        mentor.setLastName("lName3");
        mentor.setRole(User.Role.MENTOR);
        mentor.setPassword("password3");
    }

    @Test
    void saveUser() {
        userRepository.save(student1);
        User actual = userRepository.findUserByEmail("student1@gmail.com");
        Assert.assertEquals(student1.getFirstName(), actual.getFirstName());
    }

    @Test
    void deleteUser() {
        userRepository.delete(student1);
        User actual = userRepository.findUserByEmail("student1@gmail.com");
        assertThrows(NullPointerException.class, () -> actual.getFirstName());
    }

    @Test
    void findByRole() {
        userRepository.deleteAll();
        userRepository.save(student1);
        userRepository.save(student2);
        userRepository.save(mentor);
        List<User> actual = userRepository.getAllByRole(User.Role.TRAINEE);
        Assert.assertEquals(actual.size(), 2);
    }
}
