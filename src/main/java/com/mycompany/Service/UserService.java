package com.mycompany.Service;

import com.mycompany.Model.Appointment;
import com.mycompany.Model.User;
import com.mycompany.Repository.AppointmentRepository;
import com.mycompany.Repository.UserRepository;
import com.mycompany.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired private UserRepository repo;
    @Autowired private AppointmentRepository arepo;
    @Autowired private PasswordEncoder passwordEncoder;
    public UserService(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }
    public List<User> listAll(){
        return (List<User>) repo.findAll();
    }

    public boolean save(User user){

        User existingUser = repo.findByEmail(user.getEmail());
        if(existingUser!=null){
            return false;
        }
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setVerified(false);
            repo.save(user);
            return true;
        }
    }

    public void saveAppointment(Appointment appointment){
        arepo.save(appointment);
    }

    public List<Appointment> findAppointments(Integer id){
        return arepo.findByUser_Id(id);
    }
    public User findByEmail(String email){
        return repo.findByEmail(email);
    }
    public User login(String email,String password){
        User user=repo.findByEmail(email);
        return user;
    }
//    public User verification(String email){
//        User tempUser=repo.findByEmail(email);
//        boolean verified = tempUser.isVerified();
//        return U
//
//    }

    public User get(String email) {
        User result =   repo.findByEmail(email);
        return result;
    }
    public void delete(Integer id) throws UserNotFoundException {
        Long count=repo.countById(id);
        if(count== null || count==0){
            throw new UserNotFoundException("Could not find any users with ID " + id);
        }
        repo.deleteById(id);

    }
}
