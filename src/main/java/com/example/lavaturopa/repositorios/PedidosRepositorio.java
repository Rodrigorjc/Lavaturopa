package com.example.lavaturopa.repositorios;

import com.example.lavaturopa.modelos.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidosRepositorio extends JpaRepository<Pedidos, Integer> {

}
