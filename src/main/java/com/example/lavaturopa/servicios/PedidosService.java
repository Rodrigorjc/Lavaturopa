package com.example.lavaturopa.servicios;


import com.example.lavaturopa.dto.LineaDTO;
import com.example.lavaturopa.dto.PedidoCrearDTO;
import com.example.lavaturopa.dto.PedidoDTO;
import com.example.lavaturopa.modelos.Pedidos;
import com.example.lavaturopa.modelos.PrendasPedidoCatalogo;
import com.example.lavaturopa.repositorios.CatalogoRepositorio;
import com.example.lavaturopa.repositorios.ClienteRepositorio;
import com.example.lavaturopa.repositorios.PedidosRepositorio;
import com.example.lavaturopa.repositorios.PrendasRepositorio;
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
    public PedidoCrearDTO save(PedidoCrearDTO pedidoDTO){
        Pedidos pedido = new Pedidos();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setEstado(pedidoDTO.getEstado());
        //FECHA NACIMIENTO (STRING) -> LOCALTADE
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaEntrega = LocalDate.parse(pedidoDTO.getFechaEntrega(), formatter);
        pedido.setFechaEntrega(fechaEntrega);

        pedido.setCliente(clienteRepositorio.getReferenceById(pedidoDTO.getIdCliente()));

        Pedidos pedidoGuardado = pedidosRepositorio.save(pedido);

        for(LineaDTO l : pedidoDTO.getLinea()){
            PrendasPedidoCatalogo linea = new PrendasPedidoCatalogo();
            linea.setCantidad(l.getCantidad());
            linea.setPedidos(pedidoGuardado);
            linea.setPrecio(l.getPrecio());
            linea.setCatalogo(catalogoRepositorio.findById(l.getIdCatalogo()).orElse(null));
            linea.setPrendas(prendasRepositorio.getReferenceById(l.getIdPrenda()));
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

}
