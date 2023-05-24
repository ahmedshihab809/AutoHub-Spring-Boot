package com.mycompany.Repository;

import com.mycompany.Model.User;
import com.mycompany.Model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface WorkshopRepository extends CrudRepository<Workshop,Integer> {

    public Workshop findByEmail(String email);
}
