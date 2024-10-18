package com.example.lavaturopa.servicios;


import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.dto.PagarPedidoDTO;
import com.example.lavaturopa.enums.EstadoPago;
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


    public MensajeDTO pagarPedido(PagarPedidoDTO pagarPedidoDTO) {
        MensajeDTO mensajeDTO = new MensajeDTO();
        Pagos pago = pagosRepositorio.findByPedidoId(pagarPedidoDTO.getIdPedido());
        Float totalPagar = pagosRepositorio.cantidadPagarByPedidoId(pagarPedidoDTO.getIdPedido());
        if (totalPagar > 0 ){
            Float pagoEfectuado = totalPagar - pagarPedidoDTO.getCantidadPago();
            if (pagoEfectuado == 0){
                pago.setEstadoPago(EstadoPago.PAGADO);
                pago.setTotal((float) 0);
                mensajeDTO.setMensaje("El pago se ha efectuado correctamente y se ha saldado completamente. Muchas gracias.");
                pagosRepositorio.save(pago);
                return mensajeDTO;
            } else if (pagoEfectuado < 0 ) {
                pago.setEstadoPago(EstadoPago.PAGADO);
                pago.setTotal((float) 0);
                pagoEfectuado = pagoEfectuado * -1;
                mensajeDTO.setMensaje("Usted ha pagado mas de la cuenta. Se le devolvera: " + pagoEfectuado + " euros.");
                pagosRepositorio.save(pago);
                return mensajeDTO;
            } else {
                mensajeDTO.setMensaje("Pago realizado le quedan por pagar: " + pagoEfectuado + " euros.");
                pago.setEstadoPago(EstadoPago.ENPROCESO);
                pago.setTotal(totalPagar - pagoEfectuado);
                pagosRepositorio.save(pago);
                return mensajeDTO;
            }
        } else {
            mensajeDTO.setMensaje("Su pago no es necesario ya que ya ha sido pagado.");
            return  mensajeDTO;
        }

    }

}
