package com.example.lavaturopa.servicios;


import com.example.lavaturopa.dto.CatalogoDTO;
import com.example.lavaturopa.modelos.Catalogo;
import com.example.lavaturopa.repositorios.CatalogoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CatalogoService {

    private CatalogoRepositorio catalogoRepositorio;

    /**
     * Este metodo obtiene todos los registo de la tabla catalogo
     * @return
     */
    public List<CatalogoDTO> getall(){
        List<Catalogo> catalogos = catalogoRepositorio.findAll();
        List<CatalogoDTO> catalogoDTOS = new ArrayList<>();
        for(Catalogo c : catalogos){
            CatalogoDTO catalogoDTO = new CatalogoDTO();
            catalogoDTO.setTipoPrenda(c.getTipoPrenda());
            catalogoDTO.setTipoServicio(c.getTipoServicio());
            catalogoDTO.setPrecio(c.getPrecio());
            catalogoDTOS.add(catalogoDTO);
        }
        return catalogoDTOS;
    }

    /**
     * Este metodo busca un catalogo por su id
     * @param id
     * @return
     */
    public CatalogoDTO catalogoById(Integer id){
        Catalogo catalogo = catalogoRepositorio.findById(id).orElse(null);
        CatalogoDTO catalogoDTO = new CatalogoDTO();
        catalogoDTO.setPrecio(catalogo.getPrecio());
        catalogoDTO.setTipoPrenda(catalogo.getTipoPrenda());
        catalogoDTO.setTipoServicio(catalogo.getTipoServicio());
        return  catalogoDTO;
    }

    /**
     * Este metedo guarda un catalogo nuevo
     * @param catalogoDTO
     * @return
     */
    public Catalogo saveCatalogo(CatalogoDTO catalogoDTO){
        Catalogo catalogo = new Catalogo();
        catalogo.setPrecio(catalogoDTO.getPrecio());
        catalogo.setTipoPrenda(catalogoDTO.getTipoPrenda());
        catalogo.setTipoServicio(catalogoDTO.getTipoServicio());
        return catalogoRepositorio.save(catalogo);
    }

    /**
     * Este metodo guarda la edicion de un catalogo ya existente
     * @param catalogoDTO
     * @param id
     * @return
     */
    public Catalogo editCatalogo(CatalogoDTO catalogoDTO, Integer id){
        Catalogo catalogo = catalogoRepositorio.findById(id).orElse(null);
        catalogo.setPrecio(catalogoDTO.getPrecio());
        catalogo.setTipoPrenda(catalogoDTO.getTipoPrenda());
        catalogo.setTipoServicio(catalogoDTO.getTipoServicio());
        return catalogoRepositorio.save(catalogo);
    }

    /**
     * Este metodo elimina a un catalogo por su id
     * @param id
     */
    public String eliminar(Integer id){
        String mensaje = "";
        Catalogo catalogo = catalogoRepositorio.findById(id).orElse(null);
        if (catalogo == null) {
            mensaje = "El catalogo con el id indicado no existe";
        }
        try {
            catalogoRepositorio.deleteById(id);
            catalogoRepositorio.findById(id);
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
