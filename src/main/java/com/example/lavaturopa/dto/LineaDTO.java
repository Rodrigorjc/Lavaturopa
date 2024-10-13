package com.example.lavaturopa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineaDTO {
    private Integer idPrenda;
    private Integer idPedido;
    private Integer idCatalogo;
    private Integer cantidad;
    private Float precio;
}
