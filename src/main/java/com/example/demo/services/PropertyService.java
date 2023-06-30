package com.example.demo.services;

import com.example.demo.enums.EVisibility;
import com.example.demo.models.Property;
import com.example.demo.models.Tag;
import com.example.demo.repositories.PropertyRepository;
import com.example.demo.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final TagRepository tagRepository;
    public boolean deleteProperty(Long id){
        Optional<Property> property = findPropertyById(id);
        if (property.isEmpty())
            return false;
        propertyRepository.deleteById(property.get().getId());
        return true;
    }
    public Optional<Property> findPropertyById(Long id){
        return propertyRepository.findById(id);
    }
    public boolean updateProperty(Long id,Property propertyBody){
        Optional<Property> property = findPropertyById(id);
        if(property.isPresent()){
            property.get().setContracts(propertyBody.getContracts());
            property.get().setPrice(propertyBody.getPrice());
            property.get().setReview(propertyBody.getReview());
            property.get().setState(propertyBody.getState());
            property.get().setName(propertyBody.getName());
            if (property.get().getTags() != null){
                tagRepository.saveAll(property.get().getTags());
            }
            property.get().setTags(propertyBody.getTags());
            property.get().setVisibility(propertyBody.getVisibility());
            property.get().setRatings(propertyBody.getRatings());
            property.get().setPropertyImage(propertyBody.getPropertyImage());
            property.get().setUser(propertyBody.getUser());
            propertyRepository.save(property.get());
            return true;
        }
        return false;
    }

    public List<Property> findAll()
    {
        return propertyRepository.findAll();
    }


    public List<Property> getAllPublicProperties() {
        return propertyRepository.findAllByVisibility(EVisibility.PUBLIC);
    }
    public List<Property> findAllByVisibilityAndUserId(EVisibility visibility,Long userId){
        return propertyRepository.findAllByVisibilityAndUserId(visibility,userId);
    }

    public void createProperty(Property propertyBody) {
        propertyBody.setId(null);
        if (propertyBody.getTags() != null){
            tagRepository.saveAll(propertyBody.getTags());
        }
        propertyRepository.save(propertyBody);
    }
}

