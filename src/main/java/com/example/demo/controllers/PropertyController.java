package com.example.demo.controllers;

import com.example.demo.dto.auth.MessageDto;
import com.example.demo.enums.EVisibility;
import com.example.demo.models.Property;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.PropertyService;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PropertyController {
    private final PropertyService propertyService;
    private final UserRepository userRepository;
    private final UserService userService;
    //Middleware
    private Long compareJWT(Long id, String token){
        return  userService.checkJWTService(id,token);
    }
    @PostMapping("/property")
    public ResponseEntity<?> createProperty(@RequestBody Property propertyBody)
    {
        propertyService.createProperty(propertyBody);
        return ResponseEntity.status(200).body(new MessageDto("The property has been created successfully"));
    }

    @DeleteMapping("/property/{id}")
    public ResponseEntity<?> deleteProperty( @PathVariable Long id){
        Optional<Property> property = propertyService.findPropertyById(id);
        if (property.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The property specified does not exist"));
        }
        propertyService.deleteProperty(id);
        return ResponseEntity.status(200).body(new MessageDto("The property has been deleted successfully"));

    }
    @GetMapping("/property")
    public ResponseEntity<?> getAllProperties(){
        return ResponseEntity.status(200).body(propertyService.findAll());
    }
    @GetMapping("/property/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable Long id){
        Optional<Property> property = propertyService.findPropertyById(id);
        if (property.isPresent())
            return ResponseEntity.status(200).body(property.get());
        return ResponseEntity.status(404).body(new MessageDto("The property specified does not exist"));

    }
    @PutMapping("/property/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, @RequestBody @Valid Property propertyBody){
     boolean propertyUpdated =   propertyService.updateProperty(id,propertyBody);
     if(propertyUpdated){
         return ResponseEntity.status(200).body(new MessageDto("The property has been updated successfully"));
     }
     else
     {
         return ResponseEntity.status(404).body(new MessageDto("The property specified does not exist"));
     }
    }
    @GetMapping("/user/property")
    public ResponseEntity<?> getAllPublicProperties()
    {
        return ResponseEntity.status(200).body(propertyService.getAllPublicProperties());
    }

    @GetMapping("/user/{userId}/property")
    public ResponseEntity<?> getPrivateProperties(@RequestHeader(name="Authorization") String token,@PathVariable Long userId)
    {
        Long id = compareJWT(userId,token);
        if(id!=null)
            return ResponseEntity.status(200).body(propertyService.findAllByVisibilityAndUserId(EVisibility.PRIVATE,userId));
        else
            return ResponseEntity.status(404).body(new MessageDto("The property does not exist"));
    }
    @GetMapping("/user/{userId}/property/{propertyId}")
    public ResponseEntity<?> getPropertyByUser(@RequestHeader(name="Authorization") String token,@PathVariable Long userId,@PathVariable Long propertyId)
    {
        Optional<Property> property = propertyService.findPropertyById(propertyId);
        if (property.isPresent())
        {
            if (property.get().getVisibility() == EVisibility.PUBLIC)
                return ResponseEntity.status(200).body(property.get());
            Long id = compareJWT(userId,token);
            if (id != null && id.equals(property.get().getUser().getId()))
                return ResponseEntity.status(200).body(property.get());
        }
        return ResponseEntity.status(404).body(new MessageDto("The property does not exist"));
    }



    @PutMapping("/user/{userId}/property/{propertyId}")
    public ResponseEntity<?> updateUserProperty(@PathVariable Long userId,
                                                @PathVariable Long propertyId,
                                                @RequestBody Property propertyBody,
                                                @RequestHeader(name="Authorization") String token)
    {
        Long id = compareJWT(userId,token);
        Optional<Property> property = propertyService.findPropertyById(propertyId);
        if(property.isPresent()) {
            if (id != null && id.equals(property.get().getUser().getId()))
            {
                if (propertyService.updateProperty(propertyId, propertyBody))
                    return ResponseEntity.status(200).body(new MessageDto("The property has been updated successfully"));
            }
        }
        return ResponseEntity.status(404).body(new MessageDto("The property does not exist"));
    }
    @DeleteMapping("/user/{userId}/property/{propertyId}")
    public ResponseEntity<?> deleteUserProperty(@PathVariable Long userId,
                                                @PathVariable Long propertyId,
                                                @RequestHeader(name="Authorization") String token)
    {
        Optional<Property> property = propertyService.findPropertyById(propertyId);
        Long id = compareJWT(userId,token);
        if(property.isPresent()) {
            if (id != null && id.equals(property.get().getUser().getId())) {
                if (propertyService.deleteProperty(propertyId))
                    return ResponseEntity.status(200).body(new MessageDto("The property has been deleted successfully"));
            }
        }
        return ResponseEntity.status(404).body(new MessageDto("The property does not exist"));
    }
}
