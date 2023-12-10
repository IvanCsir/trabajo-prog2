package ar.edu.um.programacion2.procesador.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ar.edu.um.programacion2.procesador.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the FiltrarReportesResource REST controller.
 *
 * @see FiltrarReportesResource
 */
@IntegrationTest
class FiltrarReportesResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        FiltrarReportesResource filtrarReportesResource = new FiltrarReportesResource();
        restMockMvc = MockMvcBuilders.standaloneSetup(filtrarReportesResource).build();
    }

    /**
     * Test defaultAction
     */
    @Test
    void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/filtrar-reportes/default-action")).andExpect(status().isOk());
    }
}
