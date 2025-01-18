package com.example.lavaturopa.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prendas_pedido_catalogo", schema = "lavaturopa"/*, catalog = "postgres"*/)
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedidos pedidos;

    @ManyToOne
    @JoinColumn(name = "id_prendas", nullable = false)
    private Prendas prendas;

    @ManyToOne
    @JoinColumn(name = "id_catalogo", nullable = false)
    private Catalogo catalogo;

    @Column(name = "precio", nullable = false)
    private Float precio;

    @Column(name = "cantidad",nullable = false)
    private Integer cantidad;
}
