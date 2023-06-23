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
    public boolean updateProperty(Long id,Property propertyBody){
        Optional<Property> property = propertyRepository.findById(id);
        if(property.isPresent()){
            property.get().setContracts(propertyBody.getContracts());
            property.get().setPrice(propertyBody.getPrice());
            property.get().setReview(propertyBody.getReview());
            property.get().setState(propertyBody.getState());
            property.get().setName(propertyBody.getName());
            property.get().setTags(propertyBody.getTags());
            property.get().setVisibility(propertyBody.getVisibility());
            property.get().setRatings(propertyBody.getRatings());
            propertyRepository.save(property.get());
            return true;
        }
        return false;
    }
    public List<Property> findAll()
    {

        return propertyRepository.findAll();
    }
}

