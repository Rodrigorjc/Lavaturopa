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


    public MensajeDTO pagarPedido(PagarPedidoDTO pagarPedidoDTO) throws Exception{
        MensajeDTO mensajeDTO = new MensajeDTO();
        try {
            Pagos pago = pagosRepositorio.findByPedidoId(pagarPedidoDTO.getIdPedido());
            if (pago == null) {
                throw new Exception("No se encontró el pedido con el ID proporcionado.");
            }

            Float totalPagar = pagosRepositorio.cantidadPagarByPedidoId(pagarPedidoDTO.getIdPedido());
            if (totalPagar == null || totalPagar <= 0) {
                throw new Exception("Su pago no es necesario ya que ya ha sido pagado.");
            }

            Float pagoEfectuado = totalPagar - pagarPedidoDTO.getCantidadPago();
            if (pagoEfectuado == 0) {
                pago.setEstadoPago(EstadoPago.PAGADO);
                pago.setTotal(0f);
                mensajeDTO.setMensaje("El pago se ha efectuado correctamente y se ha saldado completamente. Muchas gracias.");
            } else if (pagoEfectuado < 0) {
                pago.setEstadoPago(EstadoPago.PAGADO);
                pago.setTotal(0f);
                pagoEfectuado = -pagoEfectuado;
                mensajeDTO.setMensaje("Usted ha pagado más de la cuenta. Se le devolverá: " + pagoEfectuado + " euros.");
            } else {
                pago.setEstadoPago(EstadoPago.ENPROCESO);
                pago.setTotal(pagoEfectuado);
                mensajeDTO.setMensaje("Pago realizado, le quedan por pagar: " + pagoEfectuado + " euros.");
            }
            pagosRepositorio.save(pago);
        } catch (Exception e) {
            throw new Exception("Ocurrió un error al procesar el pago. ");
        }
        return mensajeDTO;
    }

}
