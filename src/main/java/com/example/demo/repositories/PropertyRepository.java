package com.example.demo.repositories;

import com.example.demo.models.Property;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Long> {
@NotNull Optional<Property> findById(@NotNull Long id);

@NotNull List<Property> findAll();
List<Property> findAllByName(String name);
}
