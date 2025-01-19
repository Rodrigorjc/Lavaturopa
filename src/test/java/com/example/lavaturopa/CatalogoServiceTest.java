package com.example.lavaturopa;

import com.example.lavaturopa.dto.CatalogoDTO;
import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.enums.Estado;
import com.example.lavaturopa.enums.EstadoPago;
import com.example.lavaturopa.enums.TipoPrenda;
import com.example.lavaturopa.enums.TipoServicio;
import com.example.lavaturopa.modelos.*;
import com.example.lavaturopa.repositorios.*;
import com.example.lavaturopa.servicios.CatalogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CatalogoServiceTest {

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private CatalogoRepositorio catalogoRepositorio;

    @Autowired
    private PedidosRepositorio pedidosRepositorio;

    @Autowired
    private PrendasPedidoCatalogoRepositorio prendasPedidoCatalogoRepositorio;

    @Autowired
    private PagosRepositorio pagosRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private PrendasRepositorio prendasRepositorio;

    @BeforeEach
    public void inicializarDatos() {

        Catalogo c1 = new Catalogo();
        c1.setTipoPrenda(TipoPrenda.ABRIGO);
        c1.setTipoServicio(TipoServicio.LAVADOSECADOYPLANCHADO);
        c1.setPrecio(22.50F);

        Catalogo c2 = new Catalogo();
        c2.setTipoPrenda(TipoPrenda.ALMOHADA);
        c2.setTipoServicio(TipoServicio.LAVADO);
        c2.setPrecio(7.00F);

        catalogoRepositorio.save(c1);
        catalogoRepositorio.save(c2);

    }

    @Test
    public void testPreciosLavadoPositivo() throws Exception {
        //GIVEN

        //WHEN
        List<CatalogoDTO> catalogo = catalogoService.getall();

        //THEN
        assertNotNull(catalogo);
        assertFalse(catalogo.isEmpty());
        assertEquals(2, catalogo.size());
    }

    @Test
    public void testPreciosLavadoNegativo() throws Exception {
        //GIVEN
        catalogoRepositorio.deleteAll();

        //WHEN
        Exception exception = assertThrows(Exception.class, () -> {
            catalogoService.getall();
        });

        //THEN
        assertEquals("No hay registros en el catalogo", exception.getMessage());

    }

    @Test
    public void testDisponibilidadServicioPositivo() throws Exception {
        //GIVEN
        TipoPrenda tipoPrenda = TipoPrenda.ALMOHADA;
        TipoServicio tipoServicio = TipoServicio.LAVADO;

        //WHEN
        MensajeDTO mensajeDTO = catalogoService.disponibilidadServicio(tipoServicio, tipoPrenda);

        //THEN
        assertNotNull(mensajeDTO);
        assertEquals("Si está disponible dicho servicio para la prenda seleccionada.", mensajeDTO.getMensaje());

    }

    @Test
    public void testDisponibilidadServicioNegativo() throws Exception {
        //GIVEN
        TipoPrenda tipoPrenda = TipoPrenda.ABRIGO;
        TipoServicio tipoServicio = TipoServicio.LAVADO;

        TipoPrenda tipoPrenda1 = TipoPrenda.PANTALON;
        TipoServicio tipoServicio1 = TipoServicio.LAVADO;

        TipoPrenda tipoPrenda2 = TipoPrenda.ABRIGO;
        TipoServicio tipoServicio2 = TipoServicio.SECADOYPLANCHADO;

        TipoPrenda tipoPrenda3 = null;
        TipoServicio tipoServicio3 = null;
        //WHEN
        MensajeDTO mensajeDTO = catalogoService.disponibilidadServicio(tipoServicio,tipoPrenda);
        Exception exception = assertThrows(Exception.class, () -> {
            catalogoService.disponibilidadServicio(tipoServicio1,tipoPrenda1);
        });
        Exception exception1 = assertThrows(Exception.class, () -> {
           catalogoService.disponibilidadServicio(tipoServicio2,tipoPrenda2);
        });
        Exception exception2 = assertThrows(Exception.class, () -> {
            catalogoService.disponibilidadServicio(tipoServicio3, tipoPrenda3);
        });

        //THEN
        assertEquals("No está disponible dicho servicio para la prenda seleccionada.", mensajeDTO.getMensaje());
        assertEquals("La prenda indicada no existe", exception.getMessage());
        assertEquals("El servicio indicado no existe", exception1.getMessage());
        assertEquals("Tipo de prenda o servicio no puede ser nulo", exception2.getMessage());
    }

    @Test
    public void testEliminarCatalogo_Positive() {
        // GIVEN
        Catalogo catalogo = catalogoRepositorio.findAll().getFirst();

        // WHEN
        MensajeDTO mensajeDTO = catalogoService.eliminarCatalogo(catalogo.getId());

        // THEN
        assertEquals("Catalogo eliminado con existo.", mensajeDTO.getMensaje());
        assertFalse(catalogoRepositorio.existsById(catalogo.getId()));
    }

    @Test
    public void testEliminarCatalogo_Negative_CatalogoNoExiste() {
        // WHEN
        MensajeDTO mensajeDTO = catalogoService.eliminarCatalogo(999);

        // THEN
        assertEquals("El catalogo con el id indicado no existe. ", mensajeDTO.getMensaje());
    }


}
