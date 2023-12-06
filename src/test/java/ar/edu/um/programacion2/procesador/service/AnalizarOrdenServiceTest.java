package ar.edu.um.programacion2.procesador.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.service.impl.AnalizarOrdenServiceImpl;
import com.netflix.discovery.converters.Auto;
import java.time.Instant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

public class AnalizarOrdenServiceTest {

    @InjectMocks
    private AnalizarOrdenServiceImpl analizarOrdenService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void consultarhorario() {
        Orden orden = new Orden();
        String fechaOperacionStr = "2023-12-06T15:00:00Z";
        Instant fechaOperacion = Instant.parse(fechaOperacionStr);
        orden.setFechaOperacion(fechaOperacion);
        assertTrue(analizarOrdenService.consultarHora(orden.getFechaOperacion()));
    }

    @Test
    public void consultarAccion() {
        assertTrue(analizarOrdenService.consultarAccion("PAM"));
    }
}
