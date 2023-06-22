package com.example.demo.models;

import com.example.demo.enums.EVisibility;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.demo.enums.EState;
import java.util.Set;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private EState state;
    private EVisibility visibility;
    private double price;
    @OneToMany(cascade = CascadeType.REMOVE,mappedBy="property")
    private Set<Rating> ratings;
    @ManyToMany
    @JoinTable(name = "property_tags",joinColumns = @JoinColumn(name = "tag_id"),inverseJoinColumns = @JoinColumn(name = "property_id"))
    private Set<Tag> tags;
    @OneToMany(cascade = CascadeType.REMOVE,mappedBy="property")
    private Set<Contract> contracts;
    @OneToMany(cascade = CascadeType.REMOVE,mappedBy="property")
    private Set<Review> review;
}
