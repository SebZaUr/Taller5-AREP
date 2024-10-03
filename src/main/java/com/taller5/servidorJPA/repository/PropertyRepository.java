package com.taller5.servidorJPA.repository;

import com.taller5.servidorJPA.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property,Long> {
    Property findByAddress(String address);
}
