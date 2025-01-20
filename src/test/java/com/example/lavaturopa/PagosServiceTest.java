package com.example.lavaturopa;

import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.dto.PagarPedidoDTO;
import com.example.lavaturopa.enums.Estado;
import com.example.lavaturopa.enums.EstadoPago;
import com.example.lavaturopa.enums.TipoPrenda;
import com.example.lavaturopa.enums.TipoServicio;
import com.example.lavaturopa.modelos.*;
import com.example.lavaturopa.repositorios.*;
import com.example.lavaturopa.servicios.PagosService;
import com.example.lavaturopa.servicios.PedidosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PagosServiceTest {

    @Autowired
    private PagosService service;

    @Autowired
    private PagosRepositorio repositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private PedidosService pedidosService;

    @Autowired
    private PedidosRepositorio pedidosRepositorio;


    @Autowired
    private PrendasRepositorio prendasRepositorio;

    @Autowired
    private PrendasPedidoCatalogoRepositorio repositorio1;

    @Autowired
    private CatalogoRepositorio catalogoRepositorio;

    @BeforeEach
    public void inicializarDatos() {
        Pagos pago = new Pagos();
        pago.setId(1);
        pago.setEstadoPago(EstadoPago.ENPROCESO);
        pago.setTotal(100f);

        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Juan Perez");
        cliente1.setApellidos("Lopez");
        cliente1.setDireccion("Calle Falsa 123");
        cliente1.setTelefono(123456789);
        cliente1.setDni("20558796D");
        clienteRepositorio.save(cliente1);
        pago.setCliente(cliente1);
        Pedidos pedido1 = new Pedidos();
        pedido1.setCliente(cliente1);
        pedido1.setFechaEntrega(LocalDate.now().plusDays(10));
        pedido1.setEstado(Estado.PENDIENTE);
        pedido1.setTotal(100.00F);

        // Crear catálogo y prenda
        Catalogo catalogo1 = new Catalogo();
        catalogo1.setTipoPrenda(TipoPrenda.ABRIGO);
        catalogo1.setTipoServicio(TipoServicio.LAVADOSECADOYPLANCHADO);
        catalogo1.setPrecio(22.50F);
        catalogoRepositorio.save(catalogo1);

        Prendas prenda1 = new Prendas();
        prenda1.setNombre("Abrigo de lana");
        prenda1.setDescripcion("Muy suave");
        prendasRepositorio.save(prenda1);

        // Crear línea de pedido
        PrendasPedidoCatalogo linea1 = new PrendasPedidoCatalogo();
        linea1.setPedidos(pedido1);
        linea1.setCatalogo(catalogo1);
        linea1.setPrendas(prenda1);
        linea1.setCantidad(1);
        linea1.setPrecio(22.50F);

        List<PrendasPedidoCatalogo> lineasPedido1 = new ArrayList<>();
        lineasPedido1.add(linea1);

        pedido1.setPrendasPedidoCatalogos(lineasPedido1);

        pedidosRepositorio.save(pedido1);
        pago.setPedidos(pedido1);

        repositorio.save(pago);
    }

    @Test
    public void testPagarPedidoPositivo() throws Exception {
        PagarPedidoDTO pagarPedidoDTO = new PagarPedidoDTO();
        pagarPedidoDTO.setIdPedido(1);
        pagarPedidoDTO.setCantidadPago(100f);

        MensajeDTO resultado = service.pagarPedido(pagarPedidoDTO);

        Pagos pago = repositorio.findByPedidoId(1);
        assertNotNull(pago);
        assertEquals("El pago se ha efectuado correctamente y se ha saldado completamente. Muchas gracias.", resultado.getMensaje());
        assertEquals(EstadoPago.PAGADO, pago.getEstadoPago());
        assertEquals(0f, pago.getTotal());
    }

    @Test
    public void testPagarPedidoNegativoNoEncontrado() {
        PagarPedidoDTO pagarPedidoDTO = new PagarPedidoDTO();
        pagarPedidoDTO.setIdPedido(999);
        pagarPedidoDTO.setCantidadPago(100f);

        Exception exception = assertThrows(Exception.class, () -> {
            service.pagarPedido(pagarPedidoDTO);
        });

        assertEquals("No se encontró el pedido con el ID proporcionado.", exception.getMessage());
    }

    @Test
    public void testPagarPedidoNegativoPagoNoNecesario() {
        PagarPedidoDTO pagarPedidoDTO = new PagarPedidoDTO();
        pagarPedidoDTO.setIdPedido(1);
        pagarPedidoDTO.setCantidadPago(100f);

        Pagos pago = repositorio.findByPedidoId(1);
        assertNotNull(pago, "Pago should not be null");
        pago.setTotal(0f);
        repositorio.save(pago);

        Exception exception = assertThrows(Exception.class, () -> {
            service.pagarPedido(pagarPedidoDTO);
        });

        assertEquals("Su pago no es necesario ya que ya ha sido pagado.", exception.getMessage());
    }

}
