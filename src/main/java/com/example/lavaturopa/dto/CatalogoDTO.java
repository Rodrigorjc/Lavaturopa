package com.example.lavaturopa.dto;

import com.example.lavaturopa.enums.TipoPrenda;
import com.example.lavaturopa.enums.TipoServicio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoDTO {
    private TipoPrenda tipoPrenda;
    private TipoServicio tipoServicio;
    private Float precio;
}
