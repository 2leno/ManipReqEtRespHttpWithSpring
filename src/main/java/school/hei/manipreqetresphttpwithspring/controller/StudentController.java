package school.hei.manipreqetresphttpwithspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.manipreqetresphttpwithspring.entity.StudentService;
import school.hei.manipreqetresphttpwithspring.validator.BadRequestException;
import school.hei.manipreqetresphttpwithspring.entity.Student;
import school.hei.manipreqetresphttpwithspring.validator.StudentValidator;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;
    private final StudentValidator validator;

    public StudentController(StudentService studentService, StudentValidator validator) {
        this.studentService = studentService;
        this.validator = validator;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(value = "name", required = false) String name) {
        try {
            validator.validateName(name);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type", "text/plain")
                    .body("Welcome " + name);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "text/plain")
                    .body("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/students")
    public ResponseEntity<?> addStudents(@RequestBody List<Student> newStudents) {
        try {
            List<Student> allStudents = studentService.addStudents(newStudents);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .body(allStudents);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "text/plain")
                    .body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader(value = "Accept", required = false) String acceptHeader) {
        try {
            validator.validateAcceptHeader(acceptHeader);

            if (!validator.isSupportedFormat(acceptHeader)) {
                return ResponseEntity
                        .status(HttpStatus.NOT_IMPLEMENTED)
                        .header("Content-Type", "text/plain")
                        .body("Not Implemented: Supported formats are 'text/plain' and 'application/json'");
            }

            if (acceptHeader.equals("text/plain")) {
                String textResponse = studentService.getStudentsAsText();
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-Type", "text/plain")
                        .body(textResponse);
            } else {
                List<Student> students = studentService.getAllStudents();
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(students);
            }
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "text/plain")
                    .body("Internal Server Error: " + e.getMessage());
        }
    }
}
