package com.softserve.edu;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.repository.MarathonRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Set;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MarathonRepositoryTest {
    public static final String MARATHON1_TITLE = "marathon#1";

    @Autowired
    private MarathonRepository marathonRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void setUp() {
        Marathon marathon = new Marathon();
        marathon.setTitle(MARATHON1_TITLE);
        User student = new User() {

            {
                setEmail("mail1@gmail.com");
                setFirstName("fName1");
                setLastName("lName1");
                setRole(User.Role.TRAINEE);
                setPassword("password1");
            }
        };
        marathon.setUsers(Set.of(student));
        marathonRepository.save(marathon);

    }

    @Test
    public void getNewMarathonByTitleTest() {
        Marathon actual = marathonRepository.findByTitle(MARATHON1_TITLE);
        Assertions.assertEquals(MARATHON1_TITLE, actual.getTitle());
    }

    @Test
    public void addMarathonTest() {
        Marathon newMarathon = new Marathon();
        newMarathon.setTitle("newOneMarathon");
        int marathonsNumBeforeAdding = marathonRepository.findAll().size();
        int expectedSize = marathonsNumBeforeAdding + 1;
        marathonRepository.save(newMarathon);
        Assertions.assertEquals(expectedSize, marathonRepository.findAll().size());
    }

    @Test
    public void deleteMarathonTest() {
        Marathon actual = marathonRepository.findByTitle(MARATHON1_TITLE);
        int marathonsNumBeforeRemoving = marathonRepository.findAll().size();
        int expectedSize = marathonsNumBeforeRemoving - 1;
        marathonRepository.delete(actual);
        Assertions.assertEquals(expectedSize, marathonRepository.findAll().size());
    }
}
