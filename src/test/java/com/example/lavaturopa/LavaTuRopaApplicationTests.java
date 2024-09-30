package com.example.lavaturopa;

import com.example.lavaturopa.modelos.*;
import com.example.lavaturopa.repositorios.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LavaTuRopaApplicationTests {


    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private PrendasPedidoCatalogoRepositorio prendasPedidoCatalogoRepositorio;

    @Autowired
    private PedidosRepositorio pedidosRepositorio;

    @Autowired
    private PrendasRepositorio prendasRepositorio;

    @Autowired
    private CatalogoRepositorio catalogoRepositorio;

    @Test
    void testFindAllClientes() {
        List<Cliente> clientes = clienteRepositorio.findAll();
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    @Test
    void testFindAllPrendasPedidoCatalogo() {
        List<PrendasPedidoCatalogo> prendasPedidoCatalogoList = prendasPedidoCatalogoRepositorio.findAll();
        for (PrendasPedidoCatalogo prendasPedidoCatalogo : prendasPedidoCatalogoList) {
            System.out.println(prendasPedidoCatalogo);
        }
    }

    @Test
    void testFindAllPedidos() {
        List<Pedidos> pedidosList = pedidosRepositorio.findAll();
        for (Pedidos pedidos : pedidosList) {
            System.out.println(pedidos);
        }
    }

    @Test
    void testFindAllPrendas() {
        List<Prendas> prendasList = prendasRepositorio.findAll();
        for (Prendas prendas : prendasList) {
            System.out.println(prendas);
        }
    }

    @Test
    void testFindAllCatalogo() {
        List<Catalogo> catalogoList = catalogoRepositorio.findAll();
        for (Catalogo catalogo : catalogoList) {
            System.out.println(catalogo);
        }
    }

}
