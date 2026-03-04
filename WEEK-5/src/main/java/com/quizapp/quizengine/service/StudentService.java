package com.quizapp.quizengine.service;

import com.quizapp.quizengine.model.Student;
import com.quizapp.quizengine.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // ── CRUD Methods ──

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found: " + id));
        existing.setFirstName(updatedStudent.getFirstName());
        existing.setLastName(updatedStudent.getLastName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setDepartment(updatedStudent.getDepartment());
        existing.setAge(updatedStudent.getAge());
        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // ── Custom Query Methods ──

    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    public List<Student> getStudentsOlderThan(int age) {
        return studentRepository.findByAgeGreaterThan(age);
    }

    public List<Student> getStudentsYoungerThan(int age) {
        return studentRepository.findByAgeLessThan(age);
    }

    public List<Student> getStudentsByAgeRange(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> getStudentsByDeptAndAge(String dept, int age) {
        return studentRepository.findByDepartmentAndAgeGreaterThan(dept, age);
    }

    public List<Student> searchByName(String keyword) {
        return studentRepository.findByFirstNameContaining(keyword);
    }

    // ── Sorting Methods ──

    public List<Student> getAllStudentsSorted(String sortBy) {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
    }

    public List<Student> getAllStudentsSortedDesc(String sortBy) {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, sortBy));
    }

    // ── Pagination Methods ──

    public Page<Student> getAllStudentsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findAll(pageable);
    }

    public Page<Student> getAllStudentsPaginatedAndSorted(
            int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(
            page, size, Sort.by(Sort.Direction.ASC, sortBy));
        return studentRepository.findAll(pageable);
    }

    public Page<Student> getAllStudentsPaginatedAndSortedDesc(
            int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(
            page, size, Sort.by(Sort.Direction.DESC, sortBy));
        return studentRepository.findAll(pageable);
    }

    public Page<Student> getStudentsByDepartmentPaginated(
            String dept, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findByDepartment(dept, pageable);
    }
}