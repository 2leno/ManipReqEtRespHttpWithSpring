package school.hei.manipreqetresphttpwithspring.validator;

import org.springframework.stereotype.Component;
import school.hei.manipreqetresphttpwithspring.entity.Student;
import school.hei.manipreqetresphttpwithspring.exception.BadRequestException;

import java.util.List;

@Component
public class StudentValidator {
    public void validate(List<Student> students) {
        if (students == null || students.isEmpty()) {
            throw new BadRequestException("Student list cannot be empty");
        }

        for (Student student : students) {
            validateStudent(student);
        }
    }

    private void validateStudent(Student student) {
        if (student.getReference() == null || student.getReference().trim().isEmpty()) {
            throw new BadRequestException("Student reference cannot be null or empty");
        }

        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            throw new BadRequestException("Student first name cannot be null or empty");
        }

        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            throw new BadRequestException("Student last name cannot be null or empty");
        }

        if (student.getAge() < 0 || student.getAge() > 150) {
            throw new BadRequestException("Student age must be between 0 and 150");
        }
    }

    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("'name' parameter is required");
        }
    }

    public void validateAcceptHeader(String acceptHeader) {
        if (acceptHeader == null) {
            throw new BadRequestException("'Accept' header is required");
        }
    }

    public boolean isSupportedFormat(String acceptHeader) {
        return acceptHeader.equals("text/plain") || acceptHeader.equals("application/json");
    }
}