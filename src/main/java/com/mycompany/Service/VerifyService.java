package com.mycompany.Service;

import com.mycompany.Model.User;
import com.mycompany.Model.Workshop;
import com.mycompany.Repository.UserRepository;
import com.mycompany.Repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {
    @Autowired private UserRepository repo1;
    @Autowired private WorkshopRepository repo2;
    public void verifyUser(String email){
        User user= repo1.findByEmail(email);
        user.setVerified(true);
        repo1.save(user);
    }
    public void verifyWorkshop(String email){
        Workshop workshop= repo2.findByEmail(email);
        workshop.setVerified(true);
        repo2.save(workshop);
    }
}
