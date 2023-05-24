package com.mycompany.Repository;

import com.mycompany.Model.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment,Integer> {

    public List<Appointment> findByUser_Id(Integer id);
    public List<Appointment> findByWorkshop_Id(Integer id);

    public Appointment findAppointmentById(Integer id);
}
