package com.example.lavaturopa.repositorios;

import com.example.lavaturopa.modelos.PrendasPedidoCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PrendasPedidoCatalogoRepositorio extends JpaRepository<PrendasPedidoCatalogo, Integer> {
    List<PrendasPedidoCatalogo> findByPedidosId(Integer pedidoId);

    @Query("SELECT SUM(p.precio * p.cantidad) FROM PrendasPedidoCatalogo p WHERE p.pedidos.id = :pedidoId")
    Float findTotalPriceByPedidoId(@Param("pedidoId") Integer pedidoId);

}
