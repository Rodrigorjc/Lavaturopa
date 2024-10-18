package com.example.lavaturopa.controlers;

import com.example.lavaturopa.dto.*;
import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.modelos.Pedidos;
import com.example.lavaturopa.servicios.ClienteService;
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
    public PedidoCrearDTO guardar(@RequestBody PedidoCrearDTO pedidoDTO){
        return pedidosService.save(pedidoDTO);
    }

    @DeleteMapping()
    public void eliminar(@RequestParam Integer id){ pedidosService.deleteById(id);}

    @GetMapping("/gasto/total")
    public MensajeDTO gastoTotal(@RequestParam Integer idPedido) {return pedidosService.gastoTotal(idPedido); }
}
