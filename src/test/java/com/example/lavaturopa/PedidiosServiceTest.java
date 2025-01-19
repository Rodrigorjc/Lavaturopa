package com.example.lavaturopa;

import com.example.lavaturopa.dto.LineaDTO;
import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.dto.PedidoCrearDTO;
import com.example.lavaturopa.enums.Estado;
import com.example.lavaturopa.enums.TipoPrenda;
import com.example.lavaturopa.enums.TipoServicio;
import com.example.lavaturopa.modelos.*;
import com.example.lavaturopa.repositorios.*;
import com.example.lavaturopa.servicios.PedidosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class PedidiosServiceTest {

    @Autowired
    private PedidosService service;

    @Autowired
    private PedidosRepositorio repositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private PrendasRepositorio prendasRepositorio;

    @Autowired
    private PrendasPedidoCatalogoRepositorio repositorio1;

    @Autowired
    private CatalogoRepositorio catalogoRepositorio;

    @BeforeEach
    public void inicializarDatos() {
        // Crear y guardar cliente
        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Juan Perez");
        cliente1.setApellidos("Lopez");
        cliente1.setDireccion("Calle Falsa 123");
        cliente1.setTelefono(123456789);
        cliente1.setDni("20558796D");
        clienteRepositorio.save(cliente1);

        // Crear pedido y asociarlo con cliente
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

        repositorio.save(pedido1);

        // Crear y guardar cliente
        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Benito");
        cliente2.setApellidos("Lopez");
        cliente2.setDireccion("Calle Falsa 123");
        cliente2.setTelefono(123456789);
        cliente2.setDni("20558796D");
        clienteRepositorio.save(cliente2);

        // Crear pedido y asociarlo con cliente
        Pedidos pedido2 = new Pedidos();
        pedido2.setCliente(cliente2);
        pedido2.setFechaEntrega(LocalDate.now().plusDays(10));
        pedido2.setEstado(Estado.PENDIENTE);
        pedido2.setTotal(150.00F);

        // Crear catálogo y prenda
        Catalogo catalogo2 = new Catalogo();
        catalogo2.setTipoPrenda(TipoPrenda.ABRIGO);
        catalogo2.setTipoServicio(TipoServicio.LAVADOSECADOYPLANCHADO);
        catalogo2.setPrecio(14.50F);
        catalogoRepositorio.save(catalogo2);

        Prendas prenda2 = new Prendas();
        prenda2.setNombre("Abrigo de lana");
        prenda2.setDescripcion("Muy suave");
        prendasRepositorio.save(prenda2);

        // Crear línea de pedido
        PrendasPedidoCatalogo linea2 = new PrendasPedidoCatalogo();
        linea2.setPedidos(pedido2);
        linea2.setCatalogo(catalogo2);
        linea2.setPrendas(prenda2);
        linea2.setCantidad(1);
        linea2.setPrecio(14.50F);

        List<PrendasPedidoCatalogo> lineasPedido2 = new ArrayList<>();
        lineasPedido2.add(linea2);

        pedido2.setPrendasPedidoCatalogos(lineasPedido2);

        repositorio.save(pedido2);

        PedidoCrearDTO pedidoDTO = new PedidoCrearDTO();
        pedidoDTO.setTotal(100.0F);
        pedidoDTO.setEstado(Estado.PENDIENTE);
        pedidoDTO.setFechaEntrega("2025-12-31");
        pedidoDTO.setIdCliente(1);
        pedidoDTO.setLinea(new ArrayList<>());
    }

    @Test
    public void testTotalPedidoPositivo() throws Exception {
        Integer idPedido = 1;

        MensajeDTO mensajeDTO = service.gastoTotal(idPedido);

        assertEquals("El precio total de su pedido es: 22.5 euros.", mensajeDTO.getMensaje());

    }

    @Test
    public void testTotalPedidoNegativo() throws Exception {
        Integer idPedido = null;
        Integer idPedido1 = 999999;

        Exception exception = assertThrows(Exception.class, () -> {
            service.gastoTotal(idPedido);
        });
        Exception exception1 = assertThrows(Exception.class, () -> {
            service.gastoTotal(idPedido1);
        });

        assertEquals("El id del pedido no puede ser nulo.", exception.getMessage());
        assertEquals("El pedido con el id indicado no existe.", exception1.getMessage());

    }

    @Test
    public void testGuardarPedidoPositivo() throws Exception {
        PedidoCrearDTO pedidoDTO = new PedidoCrearDTO();
        pedidoDTO.setIdCliente(1);
        pedidoDTO.setFechaEntrega(LocalDate.now().plusDays(5).toString());
        pedidoDTO.setTotal(100.0F);
        pedidoDTO.setEstado(Estado.PENDIENTE);

        LineaDTO lineaDTO = new LineaDTO();
        lineaDTO.setIdCatalogo(1);
        lineaDTO.setIdPrenda(1);
        lineaDTO.setCantidad(2);
        lineaDTO.setPrecio(50.0F);

        List<LineaDTO> lineas = new ArrayList<>();
        lineas.add(lineaDTO);

        pedidoDTO.setLinea(lineas);

        PedidoCrearDTO resultado = service.guardar(pedidoDTO);

        assertNotNull(resultado);
        assertEquals(100.0F, resultado.getTotal());
        assertEquals(1, resultado.getLinea().size());

    }

    @Test
    public void testGuardarPedidoConExcepciones() throws Exception {
        // Test con total negativo
        PedidoCrearDTO pedidoDTO = new PedidoCrearDTO();
        pedidoDTO.setTotal(-10.0F);
        pedidoDTO.setEstado(Estado.PENDIENTE);
        pedidoDTO.setFechaEntrega("2025-12-31");
        pedidoDTO.setIdCliente(1);
        Exception exception = assertThrows(Exception.class, () -> service.guardar(pedidoDTO));
        assertEquals("El total del pedido no puede ser negativo ni 0", exception.getMessage());

        // Test con estado inválido
        pedidoDTO.setTotal(100.0F);
        pedidoDTO.setEstado(null); // Estado inválido
        pedidoDTO.setFechaEntrega("2025-12-31");
        pedidoDTO.setIdCliente(1);
        exception = assertThrows(Exception.class, () -> service.guardar(pedidoDTO));
        assertEquals("El estado no es válido.", exception.getMessage());

        // Test con fecha de entrega anterior
        pedidoDTO.setTotal(100.0F);
        pedidoDTO.setEstado(Estado.PENDIENTE);
        pedidoDTO.setFechaEntrega("2020-01-01"); // Fecha pasada
        pedidoDTO.setIdCliente(1);
        exception = assertThrows(Exception.class, () -> service.guardar(pedidoDTO));
        assertEquals("La fecha de entrega no puede ser anterior a la fecha actual", exception.getMessage());

        // Test con cliente no existente
        pedidoDTO.setTotal(100.0F);
        pedidoDTO.setEstado(Estado.PENDIENTE);
        pedidoDTO.setFechaEntrega("2025-12-31");
        pedidoDTO.setIdCliente(999); // Cliente no existe
        exception = assertThrows(Exception.class, () -> service.guardar(pedidoDTO));
        assertEquals("El cliente no existe.", exception.getMessage());

        // Test con líneas vacías
        pedidoDTO.setTotal(100.0F);
        pedidoDTO.setEstado(Estado.PENDIENTE);
        pedidoDTO.setFechaEntrega("2025-12-31");
        pedidoDTO.setIdCliente(1);
        pedidoDTO.setLinea(Collections.emptyList()); // Líneas vacías
        exception = assertThrows(Exception.class, () -> service.guardar(pedidoDTO));
        assertEquals("El pedido debe tener al menos un producto.", exception.getMessage());

        // Test con línea con catálogo y prenda nulos
        pedidoDTO.setTotal(100.0F);
        pedidoDTO.setEstado(Estado.PENDIENTE);
        pedidoDTO.setFechaEntrega("2025-12-31");
        pedidoDTO.setIdCliente(1);
        LineaDTO lineaDTO = new LineaDTO();
        lineaDTO.setCantidad(2);
        lineaDTO.setPrecio(20.0F);
        lineaDTO.setIdCatalogo(null); // Catálogo nulo
        lineaDTO.setIdPrenda(null);  // Prenda nula
        pedidoDTO.setLinea(Collections.singletonList(lineaDTO));
        exception = assertThrows(Exception.class, () -> service.guardar(pedidoDTO));
        assertEquals("La línea de pedido debe tener un catálogo y una prenda válidos", exception.getMessage());
    }
}
