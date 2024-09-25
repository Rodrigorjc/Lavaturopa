package com.example.lavaturopa.modelos;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prendas", schema = "lavaturopa", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Prendas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion",  nullable = false)
    private String descripcion;
}
