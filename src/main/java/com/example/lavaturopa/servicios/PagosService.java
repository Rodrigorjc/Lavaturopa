package com.example.lavaturopa.servicios;


import com.example.lavaturopa.modelos.Catalogo;
import com.example.lavaturopa.modelos.Pagos;
import com.example.lavaturopa.repositorios.CatalogoRepositorio;
import com.example.lavaturopa.repositorios.PagosRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PagosService {

    private PagosRepositorio pagosRepositorio;

    /**
     * Este metodo obtiene todos los pagos
     * @return
     */
    public List<Pagos> getall(){
        List<Pagos> pagos = pagosRepositorio.findAll();
        return pagos;
    }

    /**
     * Este metodo busca un cliente por su id
     * @param id
     * @return
     */
    public Pagos pagoById(Integer id){
        Pagos pagos = pagosRepositorio.findById(id).orElse(null);
        return  pagos;
    }

    /**
     * Este metedo guarda un cliente nuevo
     * @param pago
     * @return
     */
    public Pagos savePagos(Pagos pago){
        return pagosRepositorio.save(pago);
    }

    /**
     * Este metodo guarda la edicion de un cliente ya existente
     * @param pago
     * @return
     */
    public Pagos editPagos(Pagos pago){
        return pagosRepositorio.save(pago);
    }

    /**
     * Este metodo elimina a un cliente por su id
     * @param id
     */
    public void eliminar(Integer id){
        pagosRepositorio.deleteById(id);
    }

}
