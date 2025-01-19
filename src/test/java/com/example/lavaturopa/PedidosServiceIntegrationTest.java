package com.example.lavaturopa;

import com.example.lavaturopa.dto.LineaDTO;
import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.dto.PedidoCrearDTO;
import com.example.lavaturopa.enums.Estado;
import com.example.lavaturopa.modelos.Catalogo;
import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.modelos.Pedidos;
import com.example.lavaturopa.modelos.Prendas;
import com.example.lavaturopa.repositorios.*;
import com.example.lavaturopa.servicios.PedidosService;
import com.example.lavaturopa.servicios.PrendasPedidoCatalogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PedidosServiceIntegrationTest {

    @Mock
    private PedidosRepositorio pedidosRepositorio;

    @Mock
    private ClienteRepositorio clienteRepositorio;

    @Mock
    private CatalogoRepositorio catalogoRepositorio;

    @Mock
    private PrendasRepositorio prendasRepositorio;

    @Mock
    private PrendasPedidoCatalogoRepositorio prendasPedidoCatalogoRepositorio;

    @Mock
    private PrendasPedidoCatalogoService prendasPedidoCatalogoService;

    @InjectMocks
    private PedidosService pedidosService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGuardarPedido() throws Exception {
        // GIVEN
        PedidoCrearDTO pedidoDTO = new PedidoCrearDTO();
        pedidoDTO.setTotal(150.0F);
        pedidoDTO.setEstado(Estado.PENDIENTE);
        pedidoDTO.setFechaEntrega("2025-12-31");
        pedidoDTO.setIdCliente(1);
        LineaDTO lineaDTO = new LineaDTO();
        lineaDTO.setIdCatalogo(1);
        lineaDTO.setIdPrenda(1);
        lineaDTO.setCantidad(2);
        lineaDTO.setPrecio(75.0F);
        pedidoDTO.setLinea(List.of(lineaDTO));

        Cliente cliente = new Cliente();
        cliente.setId(1);
        Catalogo catalogo = new Catalogo();
        catalogo.setId(1);
        Prendas prenda = new Prendas();
        prenda.setId(1);

        when(clienteRepositorio.findById(1)).thenReturn(Optional.of(cliente));
        when(catalogoRepositorio.findById(1)).thenReturn(Optional.of(catalogo));
        when(prendasRepositorio.findById(1)).thenReturn(Optional.of(prenda));
        when(pedidosRepositorio.save(any())).thenReturn(any());

        // WHEN
        PedidoCrearDTO result = pedidosService.guardar(pedidoDTO);

        // THEN
        assertEquals(pedidoDTO.getTotal(), result.getTotal());
        assertEquals(pedidoDTO.getEstado(), result.getEstado());
        assertEquals(pedidoDTO.getFechaEntrega(), result.getFechaEntrega());
        assertEquals(pedidoDTO.getIdCliente(), result.getIdCliente());
        assertEquals(pedidoDTO.getLinea().size(), result.getLinea().size());
    }

    @Test
    public void testGastoTotal() throws Exception {
        // GIVEN
        Pedidos pedido = new Pedidos();
        pedido.setId(1);
        when(pedidosRepositorio.findById(anyInt())).thenReturn(Optional.of(pedido));
        when(prendasPedidoCatalogoRepositorio.findTotalPriceByPedidoId(anyInt())).thenReturn(150.0F);

        // WHEN
        MensajeDTO mensajeDTO = pedidosService.gastoTotal(1);

        // THEN
        assertEquals("El precio total de su pedido es: 150.0 euros.", mensajeDTO.getMensaje());
    }
}