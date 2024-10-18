package com.example.lavaturopa.modelos;

import com.example.lavaturopa.enums.EstadoPago;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pagos", schema = "lavaturopa", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "total", nullable = false)
    private Float total;



    @Column(name = "estado_pago", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EstadoPago estadoPago;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_pedidos", nullable = false)
    private Pedidos pedidos;
}
