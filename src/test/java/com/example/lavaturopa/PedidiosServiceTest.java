package com.example.lavaturopa;

import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.enums.Estado;
import com.example.lavaturopa.enums.TipoPrenda;
import com.example.lavaturopa.enums.TipoServicio;
import com.example.lavaturopa.modelos.*;
import com.example.lavaturopa.repositorios.ClienteRepositorio;
import com.example.lavaturopa.repositorios.PedidosRepositorio;
import com.example.lavaturopa.repositorios.PrendasPedidoCatalogoRepositorio;
import com.example.lavaturopa.repositorios.PrendasRepositorio;
import com.example.lavaturopa.servicios.PedidosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @BeforeEach
    public void inicializarDatos() {
        // Existing customer and order
        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Juan Perez");
        cliente1.setApellidos("Lopez");
        cliente1.setDireccion("Calle Falsa 123");
        cliente1.setTelefono(123456789);
        cliente1.setDni("20558796D");

        clienteRepositorio.save(cliente1); // Save the Cliente entity first

        Pedidos pedido1 = new Pedidos();
        pedido1.setCliente(cliente1);
        pedido1.setFechaEntrega(LocalDate.now().plusDays(10));
        pedido1.setEstado(Estado.PENDIENTE);
        pedido1.setTotal(100.00F);

        repositorio.save(pedido1); // Save the Pedidos entity

        Catalogo catalogo1 = new Catalogo();
        catalogo1.setTipoPrenda(TipoPrenda.ABRIGO);
        catalogo1.setTipoServicio(TipoServicio.LAVADOSECADOYPLANCHADO);
        catalogo1.setPrecio(22.50F);

        Prendas prenda1 = new Prendas();
        prenda1.setNombre("Abrigo de lana");
        prenda1.setDescripcion("Muy suave");
        prendasRepositorio.save(prenda1);

        PrendasPedidoCatalogo linea1 = new PrendasPedidoCatalogo();
        linea1.setPedidos(pedido1);
        linea1.setCatalogo(catalogo1);
        linea1.setPrendas(prenda1);
        linea1.setCantidad(1);
        linea1.setPrecio(22.50F);
        repositorio1.save(linea1);

        List<PrendasPedidoCatalogo> lineasPedido1 = new ArrayList<>();
        lineasPedido1.add(linea1);

        pedido1.setPrendasPedidoCatalogos(lineasPedido1);

        repositorio.save(pedido1); // Save the Pedidos entity again with the line items

        // New customer and order
        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Maria Lopez");
        cliente2.setApellidos("Lopez");
        cliente2.setDireccion("Avenida Siempre Viva 742");
        cliente2.setTelefono(987654321);
        cliente2.setDni("30568792L");

        clienteRepositorio.save(cliente2); // Save the Cliente entity first

        Pedidos pedido2 = new Pedidos();
        pedido2.setCliente(cliente2);
        pedido2.setFechaEntrega(LocalDate.now().plusDays(5));
        pedido2.setEstado(Estado.PENDIENTE);
        pedido2.setTotal(50.00F);

        repositorio.save(pedido2); // Save the Pedidos entity

        Catalogo catalogo2 = new Catalogo();
        catalogo2.setTipoPrenda(TipoPrenda.CAMISA);
        catalogo2.setTipoServicio(TipoServicio.LAVADO);
        catalogo2.setPrecio(15.00F);

        Prendas prenda2 = new Prendas();
        prenda2.setNombre("Camisa de algodón");
        prenda2.setDescripcion("Muy suave");
        prendasRepositorio.save(prenda2);

        PrendasPedidoCatalogo linea2 = new PrendasPedidoCatalogo();
        linea2.setPedidos(pedido2);
        linea2.setCatalogo(catalogo2);
        linea2.setPrendas(prenda2);
        linea2.setCantidad(2);
        linea2.setPrecio(15.00F);
        repositorio1.save(linea2);

        List<PrendasPedidoCatalogo> lineasPedido2 = new ArrayList<>();
        lineasPedido2.add(linea2);

        pedido2.setPrendasPedidoCatalogos(lineasPedido2);

        repositorio.save(pedido2);
    }

    @Test
    public void testTotalPedidoPositivo() throws Exception {
        Integer idPedido = 2;

        MensajeDTO mensajeDTO = service.gastoTotal(idPedido);

        assertEquals("El precio total de su pedido es: 15 euros.", mensajeDTO.getMensaje());

    }
}
