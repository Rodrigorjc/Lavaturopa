package com.example.lavaturopa;

import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.dto.PagarPedidoDTO;
import com.example.lavaturopa.enums.EstadoPago;
import com.example.lavaturopa.modelos.Pagos;
import com.example.lavaturopa.repositorios.PagosRepositorio;
import com.example.lavaturopa.servicios.PagosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PagosServiceIntegrationTest {

    @Mock
    private PagosRepositorio pagosRepositorio;

    @InjectMocks
    private PagosService pagosService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPagarPedidoPositivo() throws Exception {
        // GIVEN
        PagarPedidoDTO pagarPedidoDTO = new PagarPedidoDTO();
        pagarPedidoDTO.setIdPedido(1);
        pagarPedidoDTO.setCantidadPago(100f);

        Pagos pago = new Pagos();
        pago.setId(1);
        pago.setEstadoPago(EstadoPago.ENPROCESO);
        pago.setTotal(100f);

        when(pagosRepositorio.findByPedidoId(anyInt())).thenReturn(pago);
        when(pagosRepositorio.cantidadPagarByPedidoId(anyInt())).thenReturn(100f);

        // WHEN
        MensajeDTO resultado = pagosService.pagarPedido(pagarPedidoDTO);

        // THEN
        assertEquals("El pago se ha efectuado correctamente y se ha saldado completamente. Muchas gracias.", resultado.getMensaje());
        assertEquals(EstadoPago.PAGADO, pago.getEstadoPago());
        assertEquals(0f, pago.getTotal());

        // VERIFY
        verify(pagosRepositorio).findByPedidoId(anyInt());
        verify(pagosRepositorio).cantidadPagarByPedidoId(anyInt());
        verify(pagosRepositorio).save(pago);
    }
}