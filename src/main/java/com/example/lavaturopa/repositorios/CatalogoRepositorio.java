package com.example.lavaturopa.repositorios;

import com.example.lavaturopa.enums.TipoPrenda;
import com.example.lavaturopa.enums.TipoServicio;
import com.example.lavaturopa.modelos.Catalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoRepositorio extends JpaRepository<Catalogo, Integer> {

    boolean existsByTipoServicioAndTipoPrenda(TipoServicio tipoServicio, TipoPrenda tipoPrenda);


    @Query("SELECT COUNT(p) > 0 FROM PrendasPedidoCatalogo p WHERE p.catalogo.id = :catalogoId AND p.pedidos.estado != 'ENTREGADO'")
    boolean existsInPrendasPedidoCatalogoAndPedidoNotEntregado(@Param("catalogoId") Integer catalogoId);

}
