package com.taller5.ServidorJPA.repository;

import com.taller5.ServidorJPA.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property,Long> {
}
