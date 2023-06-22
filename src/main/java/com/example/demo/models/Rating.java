package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ratings")
@Getter
@Setter
@NoArgsConstructor
public class Rating {
    @EmbeddedId
    private PropertyRatingKey id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @MapsId("propertyId")
    @JoinColumn(name = "property_id")
    private Property property;
    int rating;
}
