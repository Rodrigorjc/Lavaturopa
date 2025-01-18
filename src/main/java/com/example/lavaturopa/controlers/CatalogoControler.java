package com.example.lavaturopa.controlers;


import com.example.lavaturopa.dto.CatalogoDTO;
import com.example.lavaturopa.dto.ClienteDTO;
import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.enums.TipoPrenda;
import com.example.lavaturopa.enums.TipoServicio;
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
    public List<CatalogoDTO> getAllCatalogos() throws Exception {
        return catalogoService.getall();
    }


    @GetMapping()
    public CatalogoDTO getById(@RequestParam Integer id){
        return catalogoService.catalogoById(id);
    }

    @GetMapping("/get/{id}")
    public CatalogoDTO getByIdPath(@PathVariable Integer id){
        return catalogoService.catalogoById(id);
    }

    @PostMapping()
    public Catalogo guardar(@RequestBody CatalogoDTO catalogoDTO){
        return catalogoService.saveCatalogo(catalogoDTO);
    }

    @PutMapping()
    public Catalogo editar(@RequestBody CatalogoDTO catalogoDTO, @RequestParam Integer id){
        return catalogoService.editCatalogo(catalogoDTO, id);
    }

    @DeleteMapping()
    public MensajeDTO eliminar(@RequestParam Integer id){
        return catalogoService.eliminarCatalogo(id);
    }

    @GetMapping("/servicioDisponible")
    public MensajeDTO consultarDisponibilidad(@RequestParam TipoServicio tipoServicio, @RequestParam TipoPrenda tipoPrenda) throws Exception { return catalogoService.disponibilidadServicio(tipoServicio, tipoPrenda);}
}
