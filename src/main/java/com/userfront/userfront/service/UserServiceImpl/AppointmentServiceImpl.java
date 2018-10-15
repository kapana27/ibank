package com.userfront.userfront.service.UserServiceImpl;

import com.userfront.userfront.dao.AppointmentDao;
import com.userfront.userfront.domain.Appointment;
import com.userfront.userfront.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentDao appointmentDao;


    public Appointment createAppointment(Appointment appointment) {
        return appointmentDao.save(appointment);
    }


    public List<Appointment> findAll(){
        return appointmentDao.findAll();
    }

    public Appointment findAppointment(Long id){
        return  appointmentDao.findOne(id);
    }


    public void confirmAppointment(Long id){
        Appointment appointment = findAppointment(id);
        appointment.setConfirmed(true);
        appointmentDao.save(appointment);
    }

}
