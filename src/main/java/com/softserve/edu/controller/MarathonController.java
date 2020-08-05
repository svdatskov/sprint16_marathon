package com.softserve.edu.controller;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Data
public class MarathonController {
    Logger logger = LoggerFactory.getLogger(StudentController.class);


    private MarathonService marathonService;
    private UserService studentService;

    public MarathonController(MarathonService marathonService, UserService studentService) {
        this.marathonService = marathonService;
        this.studentService = studentService;
    }

    @GetMapping("/create-marathon")
    public String createMarathon(Model model) {
        logger.info("create marathon page was opened");
        model.addAttribute("marathon", new Marathon());
        return "create-marathon";
    }

    @PostMapping("/marathons")
    public String createMarathon(@Validated @ModelAttribute Marathon marathon, BindingResult result) {
        if (result.hasErrors()) {
            return "create-marathon";
        }
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }

    @GetMapping("/marathons/edit/{id}")
    public String updateMarathon(@PathVariable long id, Model model) {
        logger.info("update marathon with id " + id + " page was opened");
        Marathon marathon = marathonService.getMarathonById(id);
        model.addAttribute("marathon", marathon);
        return "update-marathon";
    }

    @PostMapping("/marathons/edit/{id}")
    public String updateMarathon(@PathVariable long id, @ModelAttribute Marathon marathon, BindingResult result) {
        if (result.hasErrors()) {
            return "update-marathon";
        }
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }

    @GetMapping("/marathons/delete/{id}")
    public String deleteMarathon(@PathVariable long id) {
        logger.info("marathon with id " + id + " was deleted");
        marathonService.deleteMarathonById(id);
        return "redirect:/marathons";
    }

    @GetMapping("/students/{marathon_id}")
    public String getStudentsFromMarathon(@PathVariable("marathon_id") long marathonId, Model model) {
        logger.info("Get all students from marathon with id " + marathonId + " was opened");
        List<User> students = studentService.getAll().stream().filter(
                student -> student.getMarathons().stream().anyMatch(
                        marathon -> marathon.getId() == marathonId)).collect(Collectors.toList());
        Marathon marathon = marathonService.getMarathonById(marathonId);
        model.addAttribute("students", students);
        model.addAttribute("all_students", studentService.getAll());
        model.addAttribute("marathon", marathon);
        return "marathon-students";
    }

    @GetMapping("/marathons")
    public String getAllMarathons(Model model) {
        logger.info("get all marathons page was opened");

        List<Marathon> marathons = marathonService.getAll();
        model.addAttribute("marathons", marathons);
        return "marathons";
    }

}
