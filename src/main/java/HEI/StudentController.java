package HEI;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private static final List<Student> students = new ArrayList<>();

    @GetMapping("/welcome")
    public String welcome(@RequestParam("name") String name) {
        return "Welcome " + name;
    }

    @PostMapping("/students")
    public List<Student> addStudents(@RequestBody List<Student> newStudents) {
        students.addAll(newStudents);
        return new ArrayList<>(students);
    }

    @GetMapping("/students")
    public String getStudents(@RequestHeader(value = "Accept", required = false) String accept) {
        if ("text/plain".equals(accept)) {
            return students.stream()
                    .map(s -> s.firstName() + " " + s.lastName())
                    .collect(Collectors.joining(", "));
        } else {
            return "Format non supporté.";
        }
    }
}