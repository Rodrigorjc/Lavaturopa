package com.example.lavaturopa.repositorios;

import com.example.lavaturopa.enums.EstadoPago;
import com.example.lavaturopa.modelos.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepositorio extends JpaRepository<Pagos, Integer> {

    @Query("SELECT p.total FROM Pagos p WHERE p.pedidos.id = :pedidoId")
    Float cantidadPagarByPedidoId(@Param("pedidoId") Integer pedidoId);

    @Query("SELECT p FROM Pagos p WHERE p.pedidos.id = :pedidoId")
    Pagos findByPedidoId(@Param("pedidoId") Integer pedidoId);

    @Query("SELECT COUNT(p) > 0 FROM Pagos p JOIN p.pedidos ped JOIN ped.prendasPedidoCatalogos ppc WHERE ppc.catalogo.id = :catalogoId AND p.estadoPago != :estado")
    boolean existsByCatalogoIdAndEstadoNot(@Param("catalogoId") Integer catalogoId, @Param("estado") EstadoPago estado);
}
