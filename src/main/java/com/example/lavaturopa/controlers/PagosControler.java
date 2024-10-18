package com.example.lavaturopa.controlers;


import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.dto.PagarPedidoDTO;
import com.example.lavaturopa.servicios.PagosService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagos")
@AllArgsConstructor
public class PagosControler {

    private PagosService pagosService;

    @GetMapping("/pedido")
    public MensajeDTO pagarPedido(@RequestBody PagarPedidoDTO pagarPedidoDTO) { return pagosService.pagarPedido(pagarPedidoDTO);}

}
