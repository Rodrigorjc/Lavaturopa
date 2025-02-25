package com.example.lavaturopa.controlers;

import com.example.lavaturopa.dto.*;
import com.example.lavaturopa.servicios.PedidosService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@AllArgsConstructor
public class PedidoControler {

    private PedidosService pedidosService;

    @GetMapping("/listar")
    public List<PedidoDTO> getAllClientes(){
        return pedidosService.getall();
    }

    @GetMapping()
    public PedidoDTO getById(@RequestParam Integer id){
        return pedidosService.pedidoById(id);
    }

    @PostMapping()
    public PedidoCrearDTO guardar(@RequestBody PedidoCrearDTO pedidoDTO) throws Exception {
        return pedidosService.guardar(pedidoDTO);
    }

    @DeleteMapping()
    public void eliminar(@RequestParam Integer id){ pedidosService.deleteById(id);}

    @GetMapping("/gasto/total")
    public MensajeDTO gastoTotal(@RequestParam Integer idPedido) throws Exception {return pedidosService.gastoTotal(idPedido); }
}
