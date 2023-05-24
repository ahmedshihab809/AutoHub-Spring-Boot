package com.mycompany.Service;

import com.mycompany.Model.Appointment;
import com.mycompany.Model.Workshop;
import com.mycompany.Repository.AppointmentRepository;
import com.mycompany.Repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopService {
    @Autowired
    private WorkshopRepository repo;
    @Autowired
    AppointmentRepository arepo;
    @Autowired private PasswordEncoder passwordEncoder;
    public WorkshopService(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }
    public boolean save(Workshop workshop){

        Workshop existingWorkshop = repo.findByEmail(workshop.getEmail());
        if(existingWorkshop!=null){
            return false;
        }
        else{
            workshop.setPassword(passwordEncoder.encode(workshop.getPassword()));
            workshop.setVerified(false);
            repo.save(workshop);
            return true;
        }
    }
    public List<Appointment> findAppointments(Integer id){
        return arepo.findByWorkshop_Id(id);
    }

    public void deleteAppointment(Appointment appointment){
        arepo.delete(appointment);
    }
    public Appointment findAppointmentById(Integer id){
        return arepo.findAppointmentById(id);
    }
    public Workshop findById(Integer id){
        Optional<Workshop> result =  repo.findById(id);
        return result.get();
    }
    public Workshop login(String email,String password){
        Workshop workshop=repo.findByEmail(email);
        return workshop;
    }
    public Workshop get(String email) {
        Workshop result =   repo.findByEmail(email);
        return result;
    }
    public void updateWorkshop(Workshop workshop){
        repo.save(workshop);
    }

    public List<Workshop> allWorkshops(){
        return (List<Workshop>) repo.findAll();
    }
}
