package school.hei.manipreqetresphttpwithspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.manipreqetresphttpwithspring.model.Student;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api")
public class StudentController {
    private final List<Student> students = new CopyOnWriteArrayList<>();

    @GetMapping("/welcome")
    public String welcome(@RequestParam(value = "name", defaultValue = "HEI") String name) {
        return "Welcome " + name;
    }

}
