package com.mahmoud.appointmentsystem.kafka.eventsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEvent {

    private Long appointmentId;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentTime;
    private String status;
    private String eventType; // APPOINTMENT_CREATED, APPOINTMENT_CANCELED, etc.
}


