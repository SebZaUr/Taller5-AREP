package com.taller5.ServidorJPA.controller;

import com.taller5.ServidorJPA.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("v1/arep")
public class PropertyContoller {
    @Autowired
    private final PropertyService propertyService;

    public PropertyContoller(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<Object> getProperties(){
        return new ResponseEntity<>(propertyService.getAllProperties(), HttpStatus.ACCEPTED);
    }
}
