package com.example.lavaturopa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String repeatPassword;
}
