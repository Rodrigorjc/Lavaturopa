package com.example.lavaturopa.servicios;


import com.example.lavaturopa.modelos.Pedidos;
import com.example.lavaturopa.repositorios.PedidosRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidosService {

    private PedidosRepositorio pedidosRepositorio;

    /**
     * Este metodo obtiene todos los pedidos
     * @return
     */
    public List<Pedidos> getall(){
        List<Pedidos> pedidos = pedidosRepositorio.findAll();
        return pedidos;
    }

    /**
     * Este metodo busca un pedido por su id
     * @param id
     * @return
     */
    public Pedidos pedidoById(Integer id){
        return pedidosRepositorio.findById(id).orElse(null);
    }

    /**
     * >Guarda un nuevo pedido y tambien edita un pedido existente
     * @param pedido
     * @return
     */
    public Pedidos save(Pedidos pedido){
        return pedidosRepositorio.save(pedido);
    }

    /**
     * Elimina un pedido por su id
     * @param id
     */
    public void deleteById(Integer id){
        pedidosRepositorio.deleteById(id);
    }

}
