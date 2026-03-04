package com.quizapp.quizengine.repository;

import com.quizapp.quizengine.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // ── Derived Query Methods ──

    List<Student> findByDepartment(String department);

    List<Student> findByAgeGreaterThan(int age);

    List<Student> findByAgeLessThan(int age);

    List<Student> findByAgeBetween(int minAge, int maxAge);

    List<Student> findByDepartmentAndAgeGreaterThan(String department, int age);

    List<Student> findByFirstNameContaining(String keyword);

    Student findByEmail(String email);

    // ── Pagination ──

    Page<Student> findByDepartment(String department, Pageable pageable);

    // ── JPQL Custom Queries ──

    @Query("SELECT s FROM Student s WHERE s.department = :dept")
    List<Student> getStudentsByDepartment(@Param("dept") String department);

    @Query("SELECT s FROM Student s WHERE s.age > :minAge")
    List<Student> getStudentsOlderThan(@Param("minAge") int age);

    // ── Native SQL Query ──

    @Query(value = "SELECT * FROM students WHERE department = :dept",
           nativeQuery = true)
    List<Student> findByDepartmentNative(@Param("dept") String department);
}
