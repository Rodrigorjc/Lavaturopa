package com.example.lavaturopa.repositorios;

import com.example.lavaturopa.modelos.PrendasPedidoCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrendasPedidoCatalogoRepositorio extends JpaRepository<PrendasPedidoCatalogo, Integer> {
}
