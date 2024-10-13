package com.example.lavaturopa.dto;

import com.example.lavaturopa.enums.Estado;
import com.example.lavaturopa.modelos.PrendasPedidoCatalogo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private LocalDate fechaEntrega;
    private Estado estado;
    private Float total;
    private Integer idCliente;
    private List<LineaDTO> linea;
}
