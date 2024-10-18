package com.example.lavaturopa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagarPedidoDTO {
    private Integer idPedido;
    private Float cantidadPago;
}
