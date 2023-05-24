package com.mycompany.Repository;

import com.mycompany.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {

    public Long countById(Integer id);
    public User findByEmail(String email);
}
