package com.example.lavaturopa.modelos;

import com.example.lavaturopa.enums.Estado;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pedidos", schema = "lavaturopa", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha_entrega",  nullable = false)
    private LocalDate fechaEntrega;

    @Column(name = "estado",  nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Estado estado;

    @Column(name = "total",  nullable = false)
    private Float total;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
}
