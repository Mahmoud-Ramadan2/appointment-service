package com.mahmoud.appointmentsystem.controller;

import com.mahmoud.appointmentsystem.model.Appointment;
import com.mahmoud.appointmentsystem.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }
@GetMapping("test")
public String test(){
        return "EEEEEEEEEEE";
}
    // Book an appointment
    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        return service.book(appointment);
    }

    // View appointments for a doctor
    @GetMapping("/doctor/{id}")
    public List<Appointment> getDoctorAppointments(@PathVariable Long id) {

        return service.getByDoctorId(id);
    }

    // View appointments for a patient
    @GetMapping("/patient/{id}")
    public List<Appointment> getPatientAppointments(@PathVariable Long id) {

        return service.getByPatientId(id);
    }

    // Admin - view all appointments
    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return service.getAll();
    }
}