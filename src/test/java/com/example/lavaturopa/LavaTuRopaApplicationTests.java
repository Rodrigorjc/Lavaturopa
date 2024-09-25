package com.example.lavaturopa;

import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.repositorios.ClienteRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LavaTuRopaApplicationTests {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Test
    void testFindAllClientes() {
        List<Cliente> clientes = clienteRepositorio.findAll();
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

}
