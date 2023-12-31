package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRatingKey implements Serializable
{
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "property_id")
    private Long propertyId;
}
