package com.example.lavaturopa;

import com.example.lavaturopa.dto.CatalogoDTO;
import com.example.lavaturopa.dto.MensajeDTO;
import com.example.lavaturopa.enums.EstadoPago;
import com.example.lavaturopa.enums.TipoPrenda;
import com.example.lavaturopa.enums.TipoServicio;
import com.example.lavaturopa.modelos.Catalogo;
import com.example.lavaturopa.repositorios.CatalogoRepositorio;
import com.example.lavaturopa.repositorios.PagosRepositorio;
import com.example.lavaturopa.servicios.CatalogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class CatalogoServiceIntegrationTest {

    @Mock
    private CatalogoRepositorio catalogoRepositorio;

    @Mock
    private PagosRepositorio pagosRepositorio;

    @InjectMocks
    private CatalogoService catalogoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEliminarCatalogo() {
        // GIVEN
        Catalogo catalogo = new Catalogo();
        catalogo.setId(1);
        when(catalogoRepositorio.findById(anyInt())).thenReturn(Optional.of(catalogo));
        when(catalogoRepositorio.existsInPrendasPedidoCatalogoAndPedidoNotEntregado(anyInt())).thenReturn(false);
        when(pagosRepositorio.existsByCatalogoIdAndEstadoNot(anyInt(), eq(EstadoPago.PAGADO))).thenReturn(false);

        // WHEN
        MensajeDTO mensajeDTO = catalogoService.eliminarCatalogo(1);

        // THEN
        assertEquals("Catalogo eliminado con existo.", mensajeDTO.getMensaje());
        verify(catalogoRepositorio, times(1)).deleteById(1);
    }

    @Test
    public void testDisponibilidadServicio() throws Exception {
        // GIVEN
        TipoServicio tipoServicio = TipoServicio.LAVADOSECADOYPLANCHADO;
        TipoPrenda tipoPrenda = TipoPrenda.ABRIGO;

        when(catalogoRepositorio.existsByTipoServicioAndTipoPrenda(any(TipoServicio.class), any(TipoPrenda.class))).thenReturn(true);
        when(catalogoRepositorio.existsByTipoPrenda(any(TipoPrenda.class))).thenReturn(true);
        when(catalogoRepositorio.existsByTipoServicio(any(TipoServicio.class))).thenReturn(true);

        // WHEN
        MensajeDTO mensajeDTO = catalogoService.disponibilidadServicio(tipoServicio, tipoPrenda);

        // THEN
        assertEquals("Si está disponible dicho servicio para la prenda seleccionada.", mensajeDTO.getMensaje());

        // VERIFY
        verify(catalogoRepositorio).existsByTipoServicioAndTipoPrenda(any(TipoServicio.class), any(TipoPrenda.class));
        verify(catalogoRepositorio).existsByTipoPrenda(any(TipoPrenda.class));
        verify(catalogoRepositorio).existsByTipoServicio(any(TipoServicio.class));

    }

    @Test
    public void testGetAll() throws Exception {
        // GIVEN
        Catalogo catalogo1 = new Catalogo();
        catalogo1.setTipoPrenda(TipoPrenda.ABRIGO);
        catalogo1.setTipoServicio(TipoServicio.LAVADOSECADOYPLANCHADO);
        catalogo1.setPrecio(100.0F);

        Catalogo catalogo2 = new Catalogo();
        catalogo2.setTipoPrenda(TipoPrenda.CAMISA);
        catalogo2.setTipoServicio(TipoServicio.LAVADO);
        catalogo2.setPrecio(50.0F);

        when(catalogoRepositorio.findAll()).thenReturn(Arrays.asList(catalogo1, catalogo2));

        // WHEN
        List<CatalogoDTO> catalogoDTOS = catalogoService.getall();

        // THEN
        assertEquals(2, catalogoDTOS.size());
        assertEquals(TipoPrenda.ABRIGO, catalogoDTOS.getFirst().getTipoPrenda());
        assertEquals(TipoServicio.LAVADOSECADOYPLANCHADO, catalogoDTOS.get(0).getTipoServicio());
        assertEquals(100.0F, catalogoDTOS.get(0).getPrecio());
        assertEquals(TipoPrenda.CAMISA, catalogoDTOS.get(1).getTipoPrenda());
        assertEquals(TipoServicio.LAVADO, catalogoDTOS.get(1).getTipoServicio());
        assertEquals(50.0F, catalogoDTOS.get(1).getPrecio());

        // VERIFY
        verify(catalogoRepositorio).findAll();
    }

    @Test
    public void testGetAll1() {
        // GIVEN
        when(catalogoRepositorio.findAll()).thenReturn(List.of());

        // WHEN & THEN
        Exception exception = assertThrows(Exception.class, () -> catalogoService.getall());
        assertEquals("No hay registros en el catalogo", exception.getMessage());

        // VERIFY
        verify(catalogoRepositorio).findAll();
    }
}