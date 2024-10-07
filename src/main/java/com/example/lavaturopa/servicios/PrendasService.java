package com.example.lavaturopa.servicios;


import com.example.lavaturopa.modelos.Prendas;
import com.example.lavaturopa.repositorios.PrendasRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PrendasService {

    private PrendasRepositorio prendasRepositorio;

    /**
     * Este metodo obtiene todas las prendas
     * @return
     */
    public List<Prendas> getall(){
        List<Prendas> prendas = prendasRepositorio.findAll();
        return prendas;
    }

    /**
     * Este metodo busca una prenda por su id
     * @param id
     * @return
     */
    public Prendas pedidoById(Integer id){
        return prendasRepositorio.findById(id).orElse(null);
    }

    /**
     * >Guarda un nueva prenda y tambien edita una prenda existente
     * @param prendas
     * @return
     */
    public Prendas save(Prendas prendas){
        return prendasRepositorio.save(prendas);
    }

    /**
     * Elimina un pedido por su id
     * @param id
     */
    public void deleteById(Integer id){
        prendasRepositorio.deleteById(id);
    }

}
