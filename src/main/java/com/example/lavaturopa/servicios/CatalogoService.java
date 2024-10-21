package com.example.lavaturopa.servicios;


import com.example.lavaturopa.dto.CatalogoDTO;
import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.enums.EstadoPago;
import com.example.lavaturopa.enums.TipoPrenda;
import com.example.lavaturopa.enums.TipoServicio;
import com.example.lavaturopa.modelos.Catalogo;
import com.example.lavaturopa.repositorios.CatalogoRepositorio;
import com.example.lavaturopa.repositorios.PagosRepositorio;
import com.example.lavaturopa.repositorios.PrendasPedidoCatalogoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CatalogoService {

    private CatalogoRepositorio catalogoRepositorio;
    private PagosRepositorio pagosRepositorio;

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
     * Este metodo elimina un catlogo teniendo en cuenta tango el estado del pedido como el estado del pago
     * @param idCatalogo
     * @return
     */
    @Transactional
    public MensajeDTO eliminarCatalogo(Integer idCatalogo) {
        MensajeDTO mensajeDTO = new MensajeDTO();
        Catalogo catalogo = catalogoRepositorio.findById(idCatalogo).orElse(null);
        if (catalogo == null) {
            mensajeDTO.setMensaje("El catalogo con el id indicado no existe. ");
        }
        boolean existeRelacion = catalogoRepositorio.existsInPrendasPedidoCatalogoAndPedidoNotEntregado(idCatalogo);
        boolean existeRelacionpago  = pagosRepositorio.existsByCatalogoIdAndEstadoNot(idCatalogo, EstadoPago.PAGADO);
        if (existeRelacion) {
            mensajeDTO.setMensaje("No se puede eliminar el catálogo porque está asociado a un pedido que no está en estado ENTREGADO.");
            return  mensajeDTO;
        } else if (existeRelacionpago) {
            mensajeDTO.setMensaje("No se pude eliminar el catalogo porque el estado del pago no es PAGADO.");
            return mensajeDTO;
        } else {
            try {
                catalogoRepositorio.deleteById(idCatalogo);
                mensajeDTO.setMensaje("Catalogo eliminado con existo.");
                return mensajeDTO;
            } catch (Exception e){
                mensajeDTO.setMensaje("No se ha podido eliminar el catalogo.");
                return mensajeDTO;
            }
        }
    }



    /**
     * Este método consulta la disponibilidad de un servicio para un prenda
     * @param tipoServicio
     * @param tipoPrenda
     * @return
     */
    public MensajeDTO disponibilidadServicio(TipoServicio tipoServicio, TipoPrenda tipoPrenda) {
        MensajeDTO mensajeDTO = new MensajeDTO();
        boolean exists = catalogoRepositorio.existsByTipoServicioAndTipoPrenda(tipoServicio, tipoPrenda);
        if (exists) {
            mensajeDTO.setMensaje("Si está disponible dicho servicio para la prenda seleccionada.");
        } else {
            mensajeDTO.setMensaje("No está disponible dicho servicio para la prenda seleccionada.");
        }
        return mensajeDTO;
    }


}
