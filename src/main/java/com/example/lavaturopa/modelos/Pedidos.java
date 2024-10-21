package com.example.lavaturopa.modelos;

import com.example.lavaturopa.enums.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos", schema = "lavaturopa", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"cliente"})
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedidos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagos> pagos;

    @OneToMany(mappedBy = "pedidos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrendasPedidoCatalogo> prendasPedidoCatalogos;

}
