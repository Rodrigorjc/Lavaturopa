package com.example.lavaturopa.servicios;


import com.example.lavaturopa.modelos.Catalogo;
import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.repositorios.CatalogoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CatalogoService {

    private CatalogoRepositorio catalogoRepositorio;

    /**
     * Este metodo obtiene todos los registo de la tabla catalogo
     * @return
     */
    public List<Catalogo> getall(){
        List<Catalogo> catalogos = catalogoRepositorio.findAll();
        return catalogos;
    }

    /**
     * Este metodo busca un catalogo por su id
     * @param id
     * @return
     */
    public Catalogo catalogoById(Integer id){
        Catalogo catalogo = catalogoRepositorio.findById(id).orElse(null);
        return  catalogo;
    }

    /**
     * Este metedo guarda un catalogo nuevo
     * @param catalogo
     * @return
     */
    public Catalogo saveCatalogo(Catalogo catalogo){
        return catalogoRepositorio.save(catalogo);
    }

    /**
     * Este metodo guarda la edicion de un catalogo ya existente
     * @param catalogo
     * @return
     */
    public Catalogo editCatalogo(Catalogo catalogo){
        return catalogoRepositorio.save(catalogo);
    }

    /**
     * Este metodo elimina a un catalogo por su id
     * @param id
     */
    public String eliminar(Integer id){
        String mensaje = "";
        Catalogo catalogo = catalogoById(id);
        if (catalogo == null) {
            mensaje = "El catalogo con el id indicado no existe";
        }
        try {
            catalogoRepositorio.deleteById(id);
            catalogo = catalogoById(id);
            if (catalogo != null){
                mensaje = "No se ha podido eliminar el clinete";
            }
            mensaje = "Catalogo eliminado con existo";
        } catch (Exception e){
            mensaje = "No se ha podido eliminar el clinete";
        }
        return mensaje;
    }
}
