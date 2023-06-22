package com.example.demo.services;

import com.example.demo.models.Property;
import com.example.demo.repositories.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    public void deleteProperty(Long id){
        Optional<Property> property = propertyRepository.findById(id);
        property.ifPresent(value -> propertyRepository.deleteById(value.getId()));
    }
    public List<Property> findAll()
    {

        return propertyRepository.findAll();
    }
}

