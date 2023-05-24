package com.mycompany.Repository;

import com.mycompany.Model.Part;
import org.springframework.data.repository.CrudRepository;

public interface PartRepository extends CrudRepository<Part,Integer> {
    public Part findByName(String name);
}
