package com.quizapp.quizengine.controller;

import com.quizapp.quizengine.model.Student;
import com.quizapp.quizengine.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ── CRUD Endpoints ──

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id, @RequestBody Student student) {
        try {
            return ResponseEntity.ok(studentService.updateStudent(id, student));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    // ── Custom Query Endpoints ──

    @GetMapping("/department")
    public List<Student> getByDepartment(@RequestParam String dept) {
        return studentService.getStudentsByDepartment(dept);
    }

    @GetMapping("/age/above/{age}")
    public List<Student> getOlderThan(@PathVariable int age) {
        return studentService.getStudentsOlderThan(age);
    }

    @GetMapping("/age/below/{age}")
    public List<Student> getYoungerThan(@PathVariable int age) {
        return studentService.getStudentsYoungerThan(age);
    }

    @GetMapping("/age/range")
    public List<Student> getByAgeRange(
            @RequestParam int min,
            @RequestParam int max) {
        return studentService.getStudentsByAgeRange(min, max);
    }

    @GetMapping("/filter")
    public List<Student> filterByDeptAndAge(
            @RequestParam String dept,
            @RequestParam int age) {
        return studentService.getStudentsByDeptAndAge(dept, age);
    }

    @GetMapping("/search")
    public List<Student> searchByName(@RequestParam String name) {
        return studentService.searchByName(name);
    }

    // ── Sorting Endpoints ──

    // Sort ascending
    // GET /api/students/sorted?sortBy=firstName
    @GetMapping("/sorted")
    public List<Student> getSortedAsc(
            @RequestParam(defaultValue = "id") String sortBy) {
        return studentService.getAllStudentsSorted(sortBy);
    }

    // Sort descending
    // GET /api/students/sorted/desc?sortBy=age
    @GetMapping("/sorted/desc")
    public List<Student> getSortedDesc(
            @RequestParam(defaultValue = "id") String sortBy) {
        return studentService.getAllStudentsSortedDesc(sortBy);
    }

    // ── Pagination Endpoints ──

    // Pagination only
    // GET /api/students/paginated?page=0&size=2
    @GetMapping("/paginated")
    public Page<Student> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return studentService.getAllStudentsPaginated(page, size);
    }

    // Pagination + Sort ascending
    // GET /api/students/paginated/sorted?page=0&size=2&sortBy=firstName
    @GetMapping("/paginated/sorted")
    public Page<Student> getPaginatedSorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return studentService.getAllStudentsPaginatedAndSorted(page, size, sortBy);
    }

    // Pagination + Sort descending
    // GET /api/students/paginated/sorted/desc?page=0&size=2&sortBy=age
    @GetMapping("/paginated/sorted/desc")
    public Page<Student> getPaginatedSortedDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return studentService.getAllStudentsPaginatedAndSortedDesc(page, size, sortBy);
    }

    // Pagination by department
    // GET /api/students/paginated/department?dept=Computer Science&page=0&size=2
    @GetMapping("/paginated/department")
    public Page<Student> getPaginatedByDept(
            @RequestParam String dept,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return studentService.getStudentsByDepartmentPaginated(dept, page, size);
    }
}