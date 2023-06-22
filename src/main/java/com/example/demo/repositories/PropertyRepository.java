package com.example.demo.repositories;

import com.example.demo.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Long> {
Optional<Property> findById(Long id);
List<Property> findAll();
List<Property> findAllByName(String name);
}
