package com.example.demo.controllers;

import com.example.demo.models.Property;
import com.example.demo.models.User;
import com.example.demo.repositories.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PropertyController {
    private final PropertyRepository propertyRepository;
    @PostMapping("/property")
    public ResponseEntity<?> createProperty(@RequestBody Property propertyBody)
    {
        propertyRepository.save(propertyBody);
        return ResponseEntity.status(200).body("Property created successfully");
    }
    @DeleteMapping
    public ResponseEntity<?> deleteProperty(@RequestParam Long id){
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isEmpty()){
            return ResponseEntity.status(400).body("The property specified does not exist");
        }
        else
        {
            propertyRepository.deleteById(property.get().getId());
            return ResponseEntity.status(200).body("The property has been deleted");
        }
    }
}
