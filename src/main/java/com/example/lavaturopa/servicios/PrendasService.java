package com.example.lavaturopa.servicios;


import com.example.lavaturopa.dto.PrendaDTO;
import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.modelos.Prendas;
import com.example.lavaturopa.repositorios.PrendasRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PrendasService {

    private PrendasRepositorio prendasRepositorio;

    /**
     * Este metodo obtiene todas las prendas
     * @return
     */
    public List<PrendaDTO> getall(){
        List<Prendas> prendas = prendasRepositorio.findAll();
        List<PrendaDTO> prendaDTOS = new ArrayList<>();
        for(Prendas p : prendas){
            PrendaDTO prendaDTO = new PrendaDTO();
            prendaDTO.setDescripcion(p.getDescripcion());
            prendaDTO.setNombre(p.getNombre());
            prendaDTOS.add(prendaDTO);
        }
        return prendaDTOS;
    }

    /**
     * Este metodo busca una prenda por su id
     * @param id
     * @return
     */
    public PrendaDTO getById(Integer id){
        Prendas prenda = prendasRepositorio.findById(id).orElse(null);
        PrendaDTO prendaDTO = new PrendaDTO();
        prendaDTO.setNombre(prenda.getNombre());
        prendaDTO.setDescripcion(prenda.getDescripcion());
        return prendaDTO;
    }

    /**
     * >Guarda un nueva prenda y tambien edita una prenda existente
     * @param prendaDTO
     * @return
     */
    public Prendas save(PrendaDTO prendaDTO){
        Prendas prendas1 = new Prendas();
        prendas1.setDescripcion(prendaDTO.getDescripcion());
        prendas1.setNombre(prendaDTO.getNombre());
        return prendasRepositorio.save(prendas1);
    }

    /**
     * Edita una prenda existente
     * @param prendaDTO
     * @param id
     * @return
     */
    public Prendas edit(PrendaDTO prendaDTO, Integer id){
        Prendas prendas = prendasRepositorio.getReferenceById(id);
        prendas.setNombre(prendaDTO.getNombre());
        prendas.setDescripcion(prendaDTO.getDescripcion());
        return prendasRepositorio.save(prendas);
    }

    /**
     * Elimina un pedido por su id
     * @param id
     */
    public String eliminar(Integer id){
        String mensaje = "";
        Prendas prendas = prendasRepositorio.findById(id).orElse(null);
        if (prendas == null) {
            mensaje = "La prenda con el id indicado no existe";
        }
        try {
            prendasRepositorio.deleteById(id);
            prendas = prendasRepositorio.findById(id).orElse(null);
            if (prendas != null){
                mensaje = "No se ha podido eliminar la prenda";
            }
            mensaje = "Prenda eliminada con exito";
        } catch (Exception e){
            mensaje = "No se ha podido eliminar la prenda";
        }
        return mensaje;
    }

}
