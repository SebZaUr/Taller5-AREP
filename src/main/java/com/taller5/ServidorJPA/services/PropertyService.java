package com.taller5.ServidorJPA.services;

import com.taller5.ServidorJPA.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public ResponseEntity<Object> getAllProperties(){
        return (ResponseEntity<Object>) propertyRepository.findAll();
    }
}
