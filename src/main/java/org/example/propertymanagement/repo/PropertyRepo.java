package org.example.propertymanagement.repo;

import org.example.propertymanagement.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepo extends JpaRepository<Property, Long>,
        JpaSpecificationExecutor<Property> {

}
