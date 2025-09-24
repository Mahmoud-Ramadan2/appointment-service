package com.mahmoud.appointmentsystem.service;

import com.mahmoud.appointmentsystem.constants.AppointmentConstants;
import com.mahmoud.appointmentsystem.DTO.UserDTO;
import com.mahmoud.appointmentsystem.client.UserServiceClient;
import com.mahmoud.appointmentsystem.kafka.eventsDTO.AppointmentEvent;
import com.mahmoud.appointmentsystem.exception.UserNotFoundException;
import com.mahmoud.appointmentsystem.model.Appointment;
import com.mahmoud.appointmentsystem.repository.AppointmentRepository;
import com.mahmoud.appointmentsystem.kafka.AppointmentEventProducer;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.scanner.Constant;

@Service
public class AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository repository;
    private final UserServiceClient userServiceClient; // Feign Client
    private final AppointmentEventProducer eventProducer;

    public AppointmentService(AppointmentRepository repository, UserServiceClient userServiceClient, AppointmentEventProducer eventProducer) {
        this.repository = repository;
        this.userServiceClient = userServiceClient;
        this.eventProducer = eventProducer;
    }
    public Appointment book(Appointment appointment) {
        // Fetch doctor details via Feign
        UserDTO doctor = userServiceClient.getUserById(appointment.getDoctorId());
        System.out.println(" Doctor is "+doctor.toString());
        if (doctor == null || !doctor.getRoles().contains(AppointmentConstants.ROLE_DOCTOR)) {
            throw new UserNotFoundException("Doctor not found with ID: " + appointment.getDoctorId());
        }

        // Fetch patient details via Feign
        UserDTO patient = userServiceClient.getUserById(appointment.getPatientId());
        if (patient == null) {
            throw new UserNotFoundException("Patient not found with ID: " + appointment.getPatientId());
        }

        logger.info("Patient Retrieved: {}", patient);
        logger.info("Doctor Retrieved: {}", doctor);

        appointment.setStatus(AppointmentConstants.STATUS_PENDING);
        Appointment saved = repository.save(appointment);

        // Publish Kafka event
        String eventMessage = "Appointment booked: " + saved.getId() +
                " DoctorId: " + saved.getDoctorId() +
                " PatientId: " + saved.getPatientId();
       // eventProducer.sendStringEvent(eventMessage);


        AppointmentEvent appointmentEvent =
                new AppointmentEvent
                        (saved.getId(), saved.getDoctorId(), saved.getPatientId(),doctor.getEmail(), patient.getEmail(),
                        saved.getAppointmentTime(), saved.getStatus(), AppointmentConstants.EVENT_APPOINTMENT_PENDING);

        eventProducer.sendAppointmentEvent(appointmentEvent);

        return saved;

    }
    public List<Appointment> getByDoctorId(Long doctorId) {
        return repository.findByDoctorId(doctorId);
    }

    public List<Appointment> getByPatientId(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    public List<Appointment> getAll() {
        return repository.findAll();
    }
}

