package com.example.lavaturopa.controlers;


import com.example.lavaturopa.dto.ClienteDTO;
import com.example.lavaturopa.dto.PrendaDTO;
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


    @GetMapping("/listar")
    public List<PrendaDTO> getAllClientes(){
        return prendasService.getall();
    }


    @GetMapping()
    public PrendaDTO getById(@RequestParam Integer id){
        return prendasService.getById(id);
    }

    @GetMapping("/get/{id}")
    public PrendaDTO getByIdPath(@PathVariable Integer id){
        return prendasService.getById(id);
    }

    @PostMapping()
    public Prendas guardar(@RequestBody PrendaDTO prendaDTO){
        return prendasService.save(prendaDTO);
    }

    @PutMapping()
    public Prendas edit(@RequestBody PrendaDTO prendaDTO, @RequestParam Integer id){
        return prendasService.edit(prendaDTO, id);
    }

    @DeleteMapping()
    public String eliminar(@RequestParam Integer id){
        return prendasService.eliminar(id);
    }

}
