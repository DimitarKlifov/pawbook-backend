package com.dimitar.booking.dto;

import com.dimitar.booking.entities.User;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private User.Role role;

    public UserResponse(Long id, String name, String email, String phoneNumber, User.Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public User.Role getRole() { return role; }
}