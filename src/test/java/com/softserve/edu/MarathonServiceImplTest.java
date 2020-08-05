package com.softserve.edu;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.service.MarathonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Stubber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MarathonServiceImplTest {

    @MockBean
    MarathonRepository marathonRepository;

    @Autowired
    MarathonService marathonService;

    @Test
    public void getAllTest() {
        Marathon m1 = new Marathon();
        Marathon m2 = new Marathon();
        m1.setTitle("Java Online Marathons");
        m1.setId(1L);
        m2.setTitle("Python Online Marathons");
        m2.setId(2L);
        List<Marathon> expectedMarathons = List.of(m1, m2);

        Mockito.when(marathonRepository.findAll()).thenReturn(expectedMarathons);
        List<Marathon> actualMarathons = marathonService.getAll();
        Assertions.assertEquals(expectedMarathons, actualMarathons);
    }


    @Test
    public void getByIdTest() {
        Marathon expectedMarathons = new Marathon();
        expectedMarathons.setTitle("Java Online Marathons");
        expectedMarathons.setId(1L);

        Mockito.when(marathonRepository.findById(1L)).thenReturn(java.util.Optional.of(expectedMarathons));
        Marathon actualMarathon = marathonService.getMarathonById(1L);
        Assertions.assertEquals(expectedMarathons, actualMarathon);
    }

    @Test
    public void deleteByIdTest() {
        Marathon expectedMarathons = new Marathon();
        expectedMarathons.setTitle("Java Online Marathons");
        expectedMarathons.setId(1L);

        Mockito.when(marathonRepository.findById(1L)).thenReturn(java.util.Optional.of(expectedMarathons));
        marathonService.deleteMarathonById(1L);
        Mockito.verify(marathonRepository).deleteById(Mockito.any());
    }

}











//@RunWith(SpringRunner.class)
//public class MarathonServiceImplTest {
//
//    @TestConfiguration
//    static class MarathonServiceImplTestConfiguration {
//        @Bean
//        public MarathonServiceImpl marathonService() {
//            return new MarathonServiceImpl();
//        }
//    }
//
//    @Autowired
//    private MarathonService marathonService;
//
//    @MockBean
//    private MarathonRepository marathonRepository;
//
//    @BeforeAll
//    public void setup() {
//        Marathon marathon = new Marathon();
//        marathon.setTitle("MARATHON");
//        Mockito.when(marathonRepository.findByTitle("MARATHON")).thenReturn(marathon);
//    }
//
//    @Test
//    public void getAllTest() {
//
//        Marathon marathon = new Marathon();
////        System.out.println(Mockito.when(marathonRepository.findAll()).thenReturn(List<Marathon>);
//
//        Assertions.assertEquals("true", "true");
//    }
//}
