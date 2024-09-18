package com.example.Kirana.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for user registration responses.
 * This class contains the information that is returned after a successful user registration.
 */
@Data
public class UserRegistrationResponse {

    /**
     * The username of the user who has been successfully registered.
     * This is the unique identifier assigned to the newly registered user.
     */
    public UserRegistrationResponse(){

    }
    public UserRegistrationResponse(String message){
        this.message=message;
    }
    private String username;
    private String message;
}
