package school.hei.manipreqetresphttpwithspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.manipreqetresphttpwithspring.model.Student;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api")
public class StudentController {
    private final List<Student> students = new CopyOnWriteArrayList<>();

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(value = "name", required = false) String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Bad request: name is required");
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type", "text/plain")
                    .body("Welcome, " + name);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error" + e.getMessage());
        }
    }

    @PostMapping("/students")
    public ResponseEntity<?> addStudents(@RequestBody List<Student> newStudents) {
        try {
            if (newStudents == null || newStudents.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Bad request: Student List cannot be empty");
            }
            students.addAll(newStudents);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .body(students);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error" + e.getMessage());
        }
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader(value = "Accept", required = false) String acceptHeader) {
        try {
            if (acceptHeader == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Bad request: 'Accept' Header is required");
            }

            if (!acceptHeader.equals("text/plain") && !acceptHeader.equals("application/json")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_IMPLEMENTED)
                        .body("Not implemented: Supported formats are 'text/plain' and 'application/json'");
            }

            if (acceptHeader.equals("text/plain")) {
                StringBuilder response = new StringBuilder("List of students:\n");
                for (Student student : students) {
                    response.append("- ").append(student.getFullName())
                            .append(" (Ref: ").append(student.getReference())
                            .append(", Age: ").append(student.getAge()).append(")\n");
                }
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(response.toString());
            } else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(students);
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error" + e.getMessage());
        }
    }
}
