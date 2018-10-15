package com.userfront.userfront.dao;

import com.userfront.userfront.domain.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentDao  extends CrudRepository<Appointment,Long> {

    List<Appointment> findAll();

    @Query(value = "select t from Appointment t where t.id = :id")
    Appointment findOne(Long id);
}
