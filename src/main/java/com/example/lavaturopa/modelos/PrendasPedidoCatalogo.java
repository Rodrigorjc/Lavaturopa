package com.example.lavaturopa.modelos;

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
public class PrendasPedidoCatalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pedidos", nullable = false)
    private Pedidos pedidos;

    @ManyToOne
    @JoinColumn(name = "id_pedidos", nullable = false)
    private Prendas prendas;

    @ManyToOne
    @JoinColumn(name = "id_pedidos", nullable = false)
    private Catalogo catalogo;

    @Column(name = "precios", nullable = false)
    private Float precio;
}
