package com.example.demo.repositories;

import com.example.demo.enums.EStateContract;
import com.example.demo.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface ContractRepository extends JpaRepository<Contract,Long> {
    Optional<Contract> findById(Long id);
    List<Contract> findAllByName(String name);
    List<Contract> findAllByState(EStateContract state);
}
