package com.example.lavaturopa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteNewDTO {
    private String nombre;
    private String apellidos;
    private String direccion;
    private String dni;
    private Integer telefono;
}
