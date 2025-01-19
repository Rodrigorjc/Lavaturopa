package com.example.lavaturopa.servicios;


import com.example.lavaturopa.dto.LineaDTO;
import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.dto.PedidoCrearDTO;
import com.example.lavaturopa.dto.PedidoDTO;
import com.example.lavaturopa.enums.Estado;
import com.example.lavaturopa.modelos.Pedidos;
import com.example.lavaturopa.modelos.PrendasPedidoCatalogo;
import com.example.lavaturopa.repositorios.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PedidosService {

    private PedidosRepositorio pedidosRepositorio;
    private ClienteRepositorio clienteRepositorio;
    private CatalogoRepositorio catalogoRepositorio;
    private PrendasRepositorio prendasRepositorio;
    private  PrendasPedidoCatalogoRepositorio prendasPedidoCatalogoRepositorio;
    private PrendasPedidoCatalogoService prendasPedidoCatalogoService;

    /**
     * Este metodo obtiene todos los pedidos
     * @return
     */
    public List<PedidoDTO> getall(){
        List<Pedidos> pedidos = pedidosRepositorio.findAll();
        List<PedidoDTO> pedidoDTOS = new ArrayList<>();
        for(Pedidos p: pedidos){
            PedidoDTO pedidoDTO = new PedidoDTO();
            pedidoDTO.setEstado(p.getEstado());
            pedidoDTO.setIdCliente(p.getCliente().getId());
            pedidoDTO.setTotal(p.getTotal());
            pedidoDTO.setFechaEntrega(p.getFechaEntrega());
            List<PrendasPedidoCatalogo> prendasPedidoCatalogos = prendasPedidoCatalogoService.findByPedidoId(p.getId());
            List<LineaDTO> lineaDTOS = new ArrayList<>();
            for (PrendasPedidoCatalogo ppc : prendasPedidoCatalogos) {
                LineaDTO lineaDTO = new LineaDTO();
                lineaDTO.setIdPrenda(ppc.getPrendas().getId());
                lineaDTO.setIdPedido(ppc.getPedidos().getId());
                lineaDTO.setIdCatalogo(ppc.getCatalogo().getId());
                lineaDTO.setCantidad(ppc.getCantidad());
                lineaDTO.setPrecio(ppc.getPrecio());
                lineaDTOS.add(lineaDTO);
            }
            pedidoDTO.setLinea(lineaDTOS);
            pedidoDTOS.add(pedidoDTO);
        }
        return pedidoDTOS;
    }

    /**
     * Este metodo busca un pedido por su id
     * @param id
     * @return
     */
    public PedidoDTO pedidoById(Integer id){
        Pedidos pedido = pedidosRepositorio.findById(id).orElse(null);
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setFechaEntrega(pedido.getFechaEntrega());
        pedidoDTO.setEstado(pedido.getEstado());
        pedidoDTO.setTotal(pedido.getTotal());
        pedidoDTO.setIdCliente(pedido.getCliente().getId());
        List<PrendasPedidoCatalogo> prendasPedidoCatalogos = prendasPedidoCatalogoService.findByPedidoId(pedido.getId());
        List<LineaDTO> lineaDTOS = new ArrayList<>();
        for (PrendasPedidoCatalogo ppc : prendasPedidoCatalogos) {
            LineaDTO lineaDTO = new LineaDTO();
            lineaDTO.setIdPrenda(ppc.getPrendas().getId());
            lineaDTO.setIdPedido(ppc.getPedidos().getId());
            lineaDTO.setIdCatalogo(ppc.getCatalogo().getId());
            lineaDTO.setCantidad(ppc.getCantidad());
            lineaDTO.setPrecio(ppc.getPrecio());
            lineaDTOS.add(lineaDTO);
        }
        pedidoDTO.setLinea(lineaDTOS);
        return pedidoDTO;
    }

    /**
     * >Guarda un nuevo pedido
     * @param pedidoDTO
     * @return
     */
    @Transactional
    public PedidoCrearDTO guardar(PedidoCrearDTO pedidoDTO) throws Exception {
        Pedidos pedido = new Pedidos();

        if (pedidoDTO.getTotal() < 0) {
            throw new Exception("El total del pedido no puede ser negativo ni 0");
        }
        pedido.setTotal(pedidoDTO.getTotal());

        if (pedidoDTO.getEstado() == null || !Estado.isValid(pedidoDTO.getEstado().name())) {
            throw new Exception("El estado no es válido.");
        }
        pedido.setEstado(pedidoDTO.getEstado());

        // FECHA NACIMIENTO (STRING) -> LOCALDATE
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaEntrega = LocalDate.parse(pedidoDTO.getFechaEntrega(), formatter);
        if (fechaEntrega.isBefore(LocalDate.now())) {
            throw new Exception("La fecha de entrega no puede ser anterior a la fecha actual");
        }
        pedido.setFechaEntrega(fechaEntrega);

        // Verificar que el cliente existe
        if (clienteRepositorio.findById(pedidoDTO.getIdCliente()).isEmpty()) {
            throw new Exception("El cliente no existe.");
        }
        pedido.setCliente(clienteRepositorio.getReferenceById(pedidoDTO.getIdCliente()));

        // Verificar que el pedido tenga al menos una línea
        if (pedidoDTO.getLinea() == null || pedidoDTO.getLinea().isEmpty()) {
            throw new Exception("El pedido debe tener al menos un producto.");
        }

        // Guardar el pedido
        Pedidos pedidoGuardado = pedidosRepositorio.save(pedido);

        // Aquí guardamos las líneas del pedido
        for (LineaDTO l : pedidoDTO.getLinea()) {
            PrendasPedidoCatalogo linea = new PrendasPedidoCatalogo();
            linea.setCantidad(l.getCantidad());
            linea.setPedidos(pedidoGuardado);
            linea.setPrecio(l.getPrecio());
            if (l.getIdCatalogo() == null || l.getIdPrenda() == null) {
                throw new Exception("La línea de pedido debe tener un catálogo y una prenda válidos");
            }
            linea.setCatalogo(catalogoRepositorio.findById(l.getIdCatalogo()).orElseThrow(() -> new Exception("Catalogo no encontrado")));
            linea.setPrendas(prendasRepositorio.findById(l.getIdPrenda()).orElseThrow(() -> new Exception("Prenda no encontrada")));
            prendasPedidoCatalogoService.guardar(linea);
        }

        return pedidoDTO;
    }

    /**
     * Elimina un pedido por su id
     * @param id
     */
    public void deleteById(Integer id){
        pedidosRepositorio.deleteById(id);
    }


    /**
     * Este metodo te da el total de un pedido
     * @param idPedido
     * @return MensajeDTO
     */
    public MensajeDTO gastoTotal(Integer idPedido) throws Exception{
        if (idPedido == null) {
            throw new Exception("El id del pedido no puede ser nulo.");
        }
        MensajeDTO mensajeDTO = new MensajeDTO();
        Pedidos pedido = pedidosRepositorio.findById(idPedido).orElse(null);
        Float total = prendasPedidoCatalogoRepositorio.findTotalPriceByPedidoId(idPedido);
        if (pedido == null) {
           throw new Exception("El pedido con el id indicado no existe.");
        } else {
            mensajeDTO.setMensaje("El precio total de su pedido es: "+ total + " euros.");
        }
        return mensajeDTO;
    }
}
