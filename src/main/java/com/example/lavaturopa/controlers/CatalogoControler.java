package com.example.lavaturopa.controlers;


import com.example.lavaturopa.dto.ClienteDTO;
import com.example.lavaturopa.modelos.Catalogo;
import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.servicios.CatalogoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
@AllArgsConstructor
public class CatalogoControler {

    private CatalogoService catalogoService;

    @GetMapping("/listar")
    public List<Catalogo> getAllClientes(){
        List<Catalogo> catalogos = catalogoService.getall();
        return catalogos;
    }


    @GetMapping()
    public Catalogo getById(@RequestParam Integer id){
        return catalogoService.catalogoById(id);
    }

    @GetMapping("/get/{id}")
    public Catalogo getByIdPath(@PathVariable Integer id){
        Catalogo catalogo = catalogoService.catalogoById(id);
        return catalogo;
    }

    @PostMapping()
    public Catalogo guardar(@RequestBody Catalogo catalogo){
        Catalogo catalogoguardar = catalogoService.saveCatalogo(catalogo);
        return catalogoguardar;
    }

    @DeleteMapping()
    public String eliminar(@RequestParam Integer id){
        return catalogoService.eliminar(id);
    }
}
