package com.example.lavaturopa.dto;

import com.example.lavaturopa.enums.Estado;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCrearDTO {

    @NotNull(message = "La fecha de entrega no puede ser nula")
    private String fechaEntrega;

    @NotNull(message = "El estado no puede ser nulo")
    private Estado estado;

    @NotNull(message = "El total no puede ser nulo")
    private Float total;

    @NotNull(message = "El id del cliente no puede ser nulo")
    private Integer idCliente;

    @NotNull(message = "La lista de líneas no puede ser nula")
    private List<LineaDTO> linea;
}