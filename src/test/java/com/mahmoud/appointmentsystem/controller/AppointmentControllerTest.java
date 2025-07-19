package com.mahmoud.appointmentsystem.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahmoud.appointmentsystem.DTO.UserDTO;
import com.mahmoud.appointmentsystem.client.UserServiceClient;
import com.mahmoud.appointmentsystem.model.Appointment;
import com.mahmoud.appointmentsystem.repository.AppointmentRepository;
import com.mahmoud.appointmentsystem.security.JWTFilter;
import com.mahmoud.appointmentsystem.security.JWTUtil;
import com.mahmoud.appointmentsystem.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@WebMvcTest(AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(AppointmentService.class) // Use real AppointmentService
public class AppointmentControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JWTFilter jwtFilter;
    @MockBean
    private UserServiceClient userServiceClient;
    @MockBean
    private AppointmentRepository appointmentRepository;
    @MockBean
    private JWTUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;


    // Add test methods here to test the AppointmentController endpoints
    // test book post
    @Test
    @DisplayName("Test booking an appointment successfully")
    void testSuccessBookAppointment() throws Exception {
// Arrange
        Appointment inputAppointment = new Appointment(2L, 3L, LocalDateTime.parse("2025-06-20T10:30:00"), "BOOKED");
        Appointment savedAppointment = new Appointment(2L, 3L, LocalDateTime.parse("2025-06-20T10:30:00"), "BOOKED");
        String json = objectMapper.writeValueAsString(inputAppointment);
        System.out.println("Serialized JSON: " + json);

        when(appointmentRepository.save(any(Appointment.class))).thenReturn(savedAppointment);
        when(userServiceClient.getUserById(2L)).thenReturn(new UserDTO("Doctor@mail.com", "Doctor Name"));
        when(userServiceClient.getUserById(3L)).thenReturn(new UserDTO("Patient@mail.com", "Patient Name"));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(savedAppointment);


// Act
        String responseBody = mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputAppointment)))
                //assertions
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.doctorId").value(2L))
                .andExpect(jsonPath("$.status").value("BOOKED"))
            .andDo(document("appointments-book")) // generates  docs

                .andReturn().getResponse().getContentAsString();

        // Verify
        verify(userServiceClient, times(2)).getUserById(anyLong());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));

    }

    @Test
    @DisplayName("Test booking an appointment with missing doctor")
    void testBookAppointmentWithMissingDoctor() throws Exception {
        // Arrange
        Appointment inputAppointment = new Appointment(300L, 3L, LocalDateTime.parse("2025-06-20T10:30:00"), "BOOKED");

        when(userServiceClient.getUserById(300L)).thenReturn(null);
//                .thenThrow(new RuntimeException("User fetch failed Doctor not found with ID: 300"));

        // Act & Assert
        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputAppointment)))
                .andExpect(status().isNotFound())
                ;

    }


}
