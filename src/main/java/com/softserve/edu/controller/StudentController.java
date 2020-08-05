package com.softserve.edu.controller;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Data
public class StudentController {
    Logger logger = LoggerFactory.getLogger(StudentController.class);

    private UserService studentService;
    private MarathonService marathonService;

    public StudentController(UserService studentService, MarathonService marathonService) {
        this.studentService = studentService;
        this.marathonService = marathonService;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleMyCustomException(DataIntegrityViolationException exception) {
        ModelAndView modelAndView = new ModelAndView("error_page", HttpStatus.BAD_REQUEST);
        String message = "User with the same email has already existed";
        modelAndView.addObject("info", message);
        return modelAndView;
    }

    @GetMapping("/create-student")
    public String createStudent(Model model) {
        logger.info("create students page was opened");
        model.addAttribute("user", new User());
        return "create-student";
    }

    @PostMapping("students/{marathon_id}/add")
    public String createStudent(@PathVariable("marathon_id") long marathonId, @Validated @ModelAttribute User user, BindingResult result) {
        logger.info("creation Student in marathon \"" + marathonService.getMarathonById(marathonId).getTitle() + "\"");
        if (result.hasErrors()) {
            return "create-student";
        }
        studentService.addUserToMarathon(
                studentService.createOrUpdateUser(user),
                marathonService.getMarathonById(marathonId));
        return "redirect:/students/" + marathonId;
    }

    @GetMapping("students/{marathon_id}/add")
    public String createStudent(@RequestParam("user_id") long userId, @PathVariable("marathon_id") long marathonId) {
        logger.info("add students to marathon with id " + marathonId + " page was opened");
        studentService.addUserToMarathon(
                studentService.getUserById(userId),
                marathonService.getMarathonById(marathonId));
        return "redirect:/students/" + marathonId;
    }

    @GetMapping("/students/{marathon_id}/edit/{student_id}")
    public String updateStudent(@PathVariable("marathon_id") long marathonId, @PathVariable("student_id") long studentId, Model model) {
        logger.info("update students in marathon with id " + marathonId + " page was opened");
        User user = studentService.getUserById(studentId);
        model.addAttribute("user", user);
        return "update-student";
    }

    @PostMapping("/students/{marathon_id}/edit/{student_id}")
    public String updateStudent(@PathVariable("marathon_id") long marathonId, @PathVariable("student_id") long studentId, @Validated @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "update-marathon";
        }
        logger.info("student with id " + studentId + " in marathon with id " + marathonId + " page was updated");
        studentService.createOrUpdateUser(user);
        return "redirect:/students/" + marathonId;
    }

    @GetMapping("/students/{marathon_id}/delete/{student_id}")
    public String deleteStudent(@PathVariable("marathon_id") long marathonId, @PathVariable("student_id") long studentId) {
        studentService.deleteUserFromMarathon(
                studentService.getUserById(studentId),
                marathonService.getMarathonById(marathonId));
        logger.info("student with id " + studentId + " in marathon with id " + marathonId + " page was deleted");

        return "redirect:/students/" + marathonId;
    }

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        logger.info("get all students page was opened");
        List<User> students = studentService.getAll();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable long id, Model model) {
        logger.info("update student page was opened");
        User user = studentService.getUserById(id);
        model.addAttribute("user", user);
        return "update-student";
    }

    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable long id, @Validated @ModelAttribute User user, BindingResult result) {
        logger.info("edit student with id "+ id +" page was opened");
        if (result.hasErrors()) {
            logger.info("wrong data during editing student with id " + id);
            return "update-marathon";
        }
        studentService.createOrUpdateUser(user);
        return "redirect:/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable long id) {
        logger.info("student with id " + id + " was deleted");
        User student = studentService.getUserById(id);
        for (Marathon marathon : student.getMarathons()) {
            studentService.deleteUserFromMarathon(student, marathon);
        }
        studentService.deleteUserById(id);
        return "redirect:/students";
    }
}
