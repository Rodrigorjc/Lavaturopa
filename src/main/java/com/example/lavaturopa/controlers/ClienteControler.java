package com.example.lavaturopa.controlers;

import com.example.lavaturopa.dto.ClienteDTO;
import com.example.lavaturopa.dto.ClienteEditDTO;
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
        return clienteService.getall();
    }


    @GetMapping()
    public ClienteDTO getById(@RequestParam Integer id){
        return clienteService.clienteById(id);
    }

    @GetMapping("/get/{id}")
    public ClienteDTO getByIdPath(@PathVariable Integer id){
        return clienteService.clienteById(id);
    }

    @PostMapping()
    public Cliente guardar(@RequestBody ClienteNewDTO cliente){
        return clienteService.saveCliente(cliente);
    }

    @PutMapping()
    public Cliente edit(@RequestBody ClienteEditDTO clienteEditDTO, @RequestParam Integer id){
        return clienteService.editCliente(clienteEditDTO, id);
    }

    @DeleteMapping()
    public String eliminar(@RequestParam Integer id){
       return clienteService.eliminar(id);
    }

}
