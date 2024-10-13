package com.example.lavaturopa.dto;

import com.example.lavaturopa.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCrearDTO {
    private String fechaEntrega;
    private Estado estado;
    private Float total;
    private Integer idCliente;
    private List<LineaDTO> linea;
}