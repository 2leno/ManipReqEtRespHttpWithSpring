package school.hei.manipreqetresphttpwithspring.controller;

import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/students")
    public String addStudents(@RequestBody List<Student> newStudents) {
        if (newStudents == null || newStudents.isEmpty()) {
            return "No students to add";
        }

        students.addAll(newStudents);

        StringBuilder response = new StringBuilder("Students added: ");
        for (int i = 0; i < newStudents.size(); i++) {
            response.append(newStudents.get(i).getFullName());
            if (i < newStudents.size() - 1) {
                response.append(", ");
            }
        }
        return response.toString();
    }

    @GetMapping("/students")
    public String getStudents(@RequestHeader(value = "Accept", defaultValue = "text/plain") String acceptHeader) {
        if (students.isEmpty()) {
            return "No students added";
        }

        if ("text/plain".equals(acceptHeader)) {
            StringBuilder response = new StringBuilder("List of students:\n");
            for (Student student : students) {
                response.append("- ").append(student.getFullName())
                        .append(" (Ref: ").append(student.getReference())
                        .append(", Age: ").append(student.getAge()).append(")\n");
            }
            return response.toString();
        } else {
            return "Format not supported. Use Accept: text/plain";
        }
    }
}
