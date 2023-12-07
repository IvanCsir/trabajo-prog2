package ar.edu.um.programacion2.procesador.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.service.impl.AnalizarOrdenServiceImpl;
import java.time.Instant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class AnalizarOrdenServiceTest {

    @InjectMocks
    private AnalizarOrdenServiceImpl analizarOrdenService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void consultarClienteTest() {
        AnalizarOrdenServiceImpl servicioMock = mock(AnalizarOrdenServiceImpl.class);
        when(servicioMock.consultarCliente(1137)).thenReturn(true);
        assertTrue(servicioMock.consultarCliente(1137));
        verify(servicioMock).consultarCliente(1137);
    }

    @Test
    public void consultarCantidadTest() {
        Orden orden = new Orden();
        orden.setAccionId(3);
        orden.setClienteId(1138);
        AnalizarOrdenServiceImpl servicioMock = mock(AnalizarOrdenServiceImpl.class);
        when(servicioMock.consultarCantidad(orden)).thenReturn(false);
        assertFalse(servicioMock.consultarCantidad(orden));
    }

    @Test
    public void consultarhorarioTrue() {
        Orden orden = new Orden();
        String fechaOperacionStr = "2023-12-06T15:00:00Z";
        Instant fechaOperacion = Instant.parse(fechaOperacionStr);
        orden.setFechaOperacion(fechaOperacion);
        assertTrue(analizarOrdenService.consultarHora(orden.getFechaOperacion()));
    }

    @Test
    public void consultarhorarioFalse() {
        Orden orden = new Orden();
        String fechaOperacionStr = "2023-12-06T05:00:00Z";
        Instant fechaOperacion = Instant.parse(fechaOperacionStr);
        orden.setFechaOperacion(fechaOperacion);
        assertFalse(analizarOrdenService.consultarHora(orden.getFechaOperacion()));
    }
}
