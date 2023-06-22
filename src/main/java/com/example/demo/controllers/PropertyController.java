package com.example.demo.controllers;

import com.example.demo.models.Property;
import com.example.demo.models.User;
import com.example.demo.repositories.PropertyRepository;
import com.example.demo.services.PropertyService;
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
    private final PropertyService propertyService;
    @PostMapping("/property")
    public ResponseEntity<?> createProperty(@RequestBody Property propertyBody)
    {
        propertyRepository.save(propertyBody);
        return ResponseEntity.status(200).body("Property created successfully");
    }
    @DeleteMapping
    public ResponseEntity<?> deleteProperty( @RequestParam Long id){
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isEmpty()){
            return ResponseEntity.status(400).body("The property specified does not exist");
        }
        else
        {
            propertyService.deleteProperty(id);
            return ResponseEntity.status(200).body("The property has been deleted successfully");
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllProperties(){
         return ResponseEntity.status(200).body(propertyService.findAll());
    }


}
