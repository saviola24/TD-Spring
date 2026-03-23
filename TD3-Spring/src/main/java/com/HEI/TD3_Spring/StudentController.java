package com.HEI.TD3_Spring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private static final List<Student> students = new ArrayList<>();

    // ==================== A) GET /welcome ====================
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(value = "name", required = false) String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body("Le paramètre 'name' est requis.");
        }
        return ResponseEntity.ok("Welcome " + name);
    }

    // ==================== B) POST /students ====================
    @PostMapping("/students")
    public ResponseEntity<List<Student>> addStudents(@RequestBody List<Student> newStudents) {
        try {
            students.addAll(newStudents);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ArrayList<>(students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ==================== C) GET /students ====================
    @GetMapping("/students")
    public ResponseEntity<Object> getStudents(@RequestHeader(value = "Accept", required = false) String accept) {
        try {
            if (accept == null || accept.isBlank()) {
                return ResponseEntity.badRequest().body("Entête Accept manquant.");
            }

            if ("text/plain".equals(accept)) {
                String noms = students.stream()
                        .map(s -> s.firstName() + " " + s.lastName())
                        .collect(Collectors.joining(", "));
                return ResponseEntity.ok(noms);
            }

            if ("application/json".equals(accept)) {
                return ResponseEntity.ok(new ArrayList<>(students));
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body("Format non supporté.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
