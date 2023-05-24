package com.mycompany.Repository;

import com.mycompany.Model.wPart;
import com.mycompany.Model.wService;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface wServiceRepository extends CrudRepository<wService,Integer> {
    public List<wService> findByService_id(Integer id);

}
