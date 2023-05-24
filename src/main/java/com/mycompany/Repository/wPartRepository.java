package com.mycompany.Repository;

import com.mycompany.Model.wPart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface wPartRepository extends CrudRepository<wPart,Integer> {
    public List<wPart> findByPart_id(Integer id);
}
