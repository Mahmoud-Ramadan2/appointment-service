package com.mahmoud.appointmentsystem.controller;

import com.mahmoud.appointmentsystem.model.Appointment;
import com.mahmoud.appointmentsystem.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService service, AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    // Book an appointment
    @PostMapping
    public ResponseEntity<Appointment> bookAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment= appointmentService.book(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    // View appointments for a doctor
    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(@PathVariable Long id) {
        List<Appointment> Appointments= appointmentService.getByDoctorId(id);

        return ResponseEntity.ok(Appointments);
    }

    // View appointments for a patient
    @GetMapping("/patient/{id}")
    public  ResponseEntity<List<Appointment>> getPatientAppointments(@PathVariable Long id) {

        List<Appointment> Appointments= appointmentService.getByDoctorId(id);

        return ResponseEntity.ok(Appointments);
    }

    // Admin - view all appointments
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> Appointments= appointmentService.getAll();

        return ResponseEntity.ok(Appointments);    }
}