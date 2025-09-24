package com.mahmoud.appointmentsystem.DTO;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
public class UserDTO {
    private Long id;
    private String username;
    private String email;
 private Set <String>roles;

    public UserDTO() {
    }

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<String> getRoles() {
        return roles;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
