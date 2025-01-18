package com.example.lavaturopa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineaDTO {
    @NotNull(message = "El id de la prenda no puede ser nulo")
    private Integer idPrenda;

    @NotNull(message = "El id del pedido no puede ser nulo")
    private Integer idPedido;

    @NotNull(message = "El id del catálogo no puede ser nulo")
    private Integer idCatalogo;

    @NotNull(message = "La cantidad no puede ser nula")
    private Integer cantidad;

    @NotNull(message = "El precio no puede ser nulo")
    private Float precio;
}
