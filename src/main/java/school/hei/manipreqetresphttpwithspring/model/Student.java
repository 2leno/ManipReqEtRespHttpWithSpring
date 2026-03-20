package school.hei.manipreqetresphttpwithspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String reference;
    private String firstName;
    private String lastName;
    private int age;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
