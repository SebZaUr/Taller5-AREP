package com.taller5.servidorJPA.services;

import com.taller5.servidorJPA.model.Property;
import com.taller5.servidorJPA.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getAllProperties(){
        return  propertyRepository.findAll();
    }

    public Property getPropertyByAddress(String address){
        return propertyRepository.findByAddress(address);
    }

    public Property addProperty(Property property){
        if(propertyRepository.findByAddress(property.getAddress()) != null){
            throw new RuntimeException("The property already exists");
        }
        return propertyRepository.save(property);
    }

    public Property updateProperty(Property property){
        Property propertyToUpdate = propertyRepository.findByAddress(property.getAddress());
        if(propertyToUpdate == null){
            throw new RuntimeException("The property does not exist");
        }
        propertyToUpdate.setPrice(property.getPrice());
        propertyToUpdate.setSize(property.getSize());
        propertyToUpdate.setDescription(property.getDescription());
        return propertyRepository.save(propertyToUpdate);
    }

    public void deleteProperty(Long id){
        Property property = propertyRepository.findById(id).orElseThrow(() -> new RuntimeException("The property does not exist"));
        propertyRepository.delete(property);
    }
}
