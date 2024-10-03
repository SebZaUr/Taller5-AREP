package com.taller5.servidorJPA.controller;

import com.taller5.servidorJPA.model.Property;
import com.taller5.servidorJPA.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("v1/arep/property")
public class PropertyContoller {
    @Autowired
    private final PropertyService propertyService;

    public PropertyContoller(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping(value="/")
    public String index() {
        return "index";
    }

    @GetMapping()
    public ResponseEntity<Object> getProperties(){
        try{
            return new ResponseEntity<>(propertyService.getAllProperties(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, e);
            if(e.getMessage().equals(" The property already exists")){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
            }else{
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @GetMapping(value = "/{address}")
    public ResponseEntity<Object> getPropertyByAddres(@PathVariable("address") String address){
        try{
            return new ResponseEntity<>(propertyService.getPropertyByAddress(address), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addProperty(@RequestBody Property property){
        try{
            return new ResponseEntity<>(propertyService.addProperty(property), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping(value = "/{address}")
    public ResponseEntity<Object> updateProperty(@RequestBody Property property){
        try{
            return new ResponseEntity<>(propertyService.updateProperty(property), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteProperty(@PathVariable("id") String id){
        try{
            propertyService.deleteProperty(Long.parseLong(id));
            return new ResponseEntity<>("Property deleted", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
