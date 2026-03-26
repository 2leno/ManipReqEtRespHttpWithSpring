package school.hei.manipreqetresphttpwithspring.entity;

import org.springframework.stereotype.Service;
import school.hei.manipreqetresphttpwithspring.validator.StudentValidator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class StudentService {

    private final List<Student> students = new CopyOnWriteArrayList<>();
    private final StudentValidator validator;

    public StudentService(StudentValidator validator) {
        this.validator = validator;
    }

    public List<Student> addStudents(List<Student> newStudents) {
        validator.validate(newStudents);
        students.addAll(newStudents);
        return students;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public String getStudentsAsText() {
        if (students.isEmpty()) {
            return "No students added";
        }

        StringBuilder response = new StringBuilder("List of students:\n");
        for (Student student : students) {
            response.append("- ").append(student.getFullName())
                    .append(" (Ref: ").append(student.getReference())
                    .append(", Age: ").append(student.getAge()).append(")\n");
        }
        return response.toString();
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }
}
