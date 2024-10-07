package com.example.lavaturopa.servicios;


import com.example.lavaturopa.modelos.PrendasPedidoCatalogo;
import com.example.lavaturopa.repositorios.PrendasPedidoCatalogoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PrendasPedidoCatalogoService {

    private PrendasPedidoCatalogoRepositorio prendasPedidoCatalogoRepositorio;

    /**
     * Obtiene todas los registros de la tabla PrendasPedidoCatalogo
     * @return
     */
    public List<PrendasPedidoCatalogo> getAll(){
        List<PrendasPedidoCatalogo> prendasPedidoCatalogos = prendasPedidoCatalogoRepositorio.findAll();
        return prendasPedidoCatalogos;
    }

    /**
     * Obtiene el registro de la tabla PrendasPedidoCatalogo por su id
     * @param id
     * @return
     */
    public PrendasPedidoCatalogo prendasPedidoCatalogoById(Integer id) {
        return prendasPedidoCatalogoRepositorio.findById(id).orElse(null);
    }

    /**
     * Guardar o modifica un registro en la tabla PrendasPedidoCatalogo
     * @param prendasPedidoCatalogo
     * @return
     */
    public PrendasPedidoCatalogo guardar(PrendasPedidoCatalogo prendasPedidoCatalogo) {
        return prendasPedidoCatalogoRepositorio.save(prendasPedidoCatalogo);
    }

    /**
     * Elimina el registro de la tabla PrendasPedidoCatalogo por su id
     * @param id
     */
    public void eliminarById(Integer id) {
        prendasPedidoCatalogoRepositorio.deleteById(id);
    }
}
