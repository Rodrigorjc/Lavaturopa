package com.example.lavaturopa.modelos;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.Set;

import java.time.LocalDate;

@Entity
@Table(name = "cliente", schema = "lavaturopa", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre",  nullable = false)
    private String nombre;

    @Column(name = "apellidos",  nullable = false)
    private String apellidos;

    @Column(name = "direccion",  nullable = false)
    private String direccion;

    @Column(name = "dni",  nullable = false)
    private String dni;

    @Column(name = "telefono",  nullable = false)
    private Integer telefono;


}
