
package com.quizapp.quizengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizapp.quizengine.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository gives you these for FREE:
    // save(), findAll(), findById(), deleteById()
    // No need to write any SQL!
}
