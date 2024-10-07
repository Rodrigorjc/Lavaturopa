package com.example.lavaturopa.servicios;


import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.repositorios.ClienteRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {


    private ClienteRepositorio clienteRepositorio;

    /**
     * Este metdo busca a todos los clientes con un DNI en concreto
     * @param dni
     * @return
     */
    public List<Cliente> getClientepoDNI(String dni) {
        List<Cliente> clientes = clienteRepositorio.findAllByDniEquals(dni);
        return clientes;
    }

    /**
     * Este metodo obtiene todos los clientes
     * @return
     */
    public List<Cliente> getall(){
        List<Cliente> clientes = clienteRepositorio.findAll();
        return clientes;
    }

    /**
     * Este metodo busca un cliente por su id
     * @param id
     * @return
     */
    public Cliente clienteById(Integer id){
        Cliente cliente = clienteRepositorio.findById(id).orElse(null);
        return cliente;
    }

    /**
     * Este metedo guarda un cliente nuevo
     * @param cliente
     * @return
     */
    public Cliente saveCliente(Cliente cliente){
        return clienteRepositorio.save(cliente);
    }

    /**
     * Este metodo guarda la edicion de un cliente ya existente
     * @param cliente
     * @return
     */
    public Cliente editCliente(Cliente cliente){
        return clienteRepositorio.save(cliente);
    }

    /**
     * Este metodo elimina a un cliente por su id
     * @param id
     */
    public void eliminar(Integer id){
        clienteRepositorio.deleteById(id);
    }
}
