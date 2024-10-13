package com.example.lavaturopa.modelos;

import com.example.lavaturopa.enums.TipoPrenda;
import com.example.lavaturopa.enums.TipoServicio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "catalogo", schema = "lavaturopa", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Catalogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //Enumerado de tipo_servicio
    @Column(name = "tipo_servicio", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TipoServicio tipoServicio;

    //Enumerado de tipo_prenda
    @Column(name = "tipo_prenda", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TipoPrenda tipoPrenda;

    @Column(name = "precio", nullable = false)
    private Float precio;
}
