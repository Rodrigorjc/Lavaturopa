package com.example.lavaturopa.controlers;


import com.example.lavaturopa.dto.ClienteDTO;
import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.modelos.Prendas;
import com.example.lavaturopa.servicios.PrendasService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prendas")
@AllArgsConstructor
public class PrendasControler {

    private PrendasService prendasService;
//
//
//    @GetMapping("/listar")
//    public List<Prendas> getAllClientes(){
//        List<Prendas> prendas = prendasService.getall();
//        return prendas;
//    }
//
//
//    @GetMapping()
//    public Cliente getById(@RequestParam Integer id){
//        return clienteService.clienteById(id);
//    }
//
//    @GetMapping("/get/{id}")
//    public Cliente getByIdPath(@PathVariable Integer id){
//        Cliente cliente = clienteService.clienteById(id);
//        return cliente;
//    }
//
//    @PostMapping()
//    public Cliente guardar(@RequestBody Cliente cliente){
//        Cliente clienteguardado = clienteService.saveCliente(cliente);
//        return clienteguardado;
//    }
//
//    @DeleteMapping()
//    public String eliminar(@RequestParam Integer id){
//        return clienteService.eliminar(id);
//    }

}
