package com.example.lavaturopa.controlers;

import com.example.lavaturopa.dto.ClienteDTO;
import com.example.lavaturopa.dto.ClienteNewDTO;
import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.servicios.ClienteService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteControler {


    private ClienteService clienteService;

    @GetMapping("/listar")
    public List<ClienteDTO> getAllClientes(){
        List<ClienteDTO> clientes = clienteService.getall();
        return clientes;
    }


    @GetMapping()
    public ClienteDTO getById(@RequestParam Integer id){
        return clienteService.clienteById(id);
    }

    @GetMapping("/get/{id}")
    public ClienteDTO getByIdPath(@PathVariable Integer id){
        ClienteDTO cliente = clienteService.clienteById(id);
        return cliente;
    }

    @PostMapping()
    public ClienteNewDTO guardar(@RequestBody Cliente cliente){
        ClienteNewDTO clienteguardado = clienteService.saveCliente(cliente);
        return clienteguardado;
    }

    @DeleteMapping()
    public String eliminar(@RequestParam Integer id){
       return clienteService.eliminar(id);
    }

}
