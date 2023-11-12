package ar.edu.um.programacion2.procesador.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.programacion2.procesador.IntegrationTest;
import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.repository.OrdenRepository;
import ar.edu.um.programacion2.procesador.service.dto.OrdenDTO;
import ar.edu.um.programacion2.procesador.service.mapper.OrdenMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrdenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdenResourceIT {

    private static final Integer DEFAULT_CLIENTE_ID = 1;
    private static final Integer UPDATED_CLIENTE_ID = 2;

    private static final Integer DEFAULT_ACCION_ID = 1;
    private static final Integer UPDATED_ACCION_ID = 2;

    private static final String DEFAULT_CODIGO_ACCION = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ACCION = "BBBBBBBBBB";

    private static final String DEFAULT_OPERACION = "AAAAAAAAAA";
    private static final String UPDATED_OPERACION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Instant DEFAULT_FECHA_OPERACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_OPERACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODO = "AAAAAAAAAA";
    private static final String UPDATED_MODO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OPERACION_EXITOSA = false;
    private static final Boolean UPDATED_OPERACION_EXITOSA = true;

    private static final String DEFAULT_OPERACION_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OPERACION_OBSERVACIONES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EJECUTADA = false;
    private static final Boolean UPDATED_EJECUTADA = true;

    private static final Boolean DEFAULT_REPORTADA = false;
    private static final Boolean UPDATED_REPORTADA = true;

    private static final String ENTITY_API_URL = "/api/ordens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private OrdenMapper ordenMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdenMockMvc;

    private Orden orden;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orden createEntity(EntityManager em) {
        Orden orden = new Orden()
            .clienteId(DEFAULT_CLIENTE_ID)
            .accionId(DEFAULT_ACCION_ID)
            .codigoAccion(DEFAULT_CODIGO_ACCION)
            .operacion(DEFAULT_OPERACION)
            .precio(DEFAULT_PRECIO)
            .cantidad(DEFAULT_CANTIDAD)
            .fechaOperacion(DEFAULT_FECHA_OPERACION)
            .modo(DEFAULT_MODO)
            .operacionExitosa(DEFAULT_OPERACION_EXITOSA)
            .operacionObservaciones(DEFAULT_OPERACION_OBSERVACIONES)
            .ejecutada(DEFAULT_EJECUTADA)
            .reportada(DEFAULT_REPORTADA);
        return orden;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orden createUpdatedEntity(EntityManager em) {
        Orden orden = new Orden()
            .clienteId(UPDATED_CLIENTE_ID)
            .accionId(UPDATED_ACCION_ID)
            .codigoAccion(UPDATED_CODIGO_ACCION)
            .operacion(UPDATED_OPERACION)
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD)
            .fechaOperacion(UPDATED_FECHA_OPERACION)
            .modo(UPDATED_MODO)
            .operacionExitosa(UPDATED_OPERACION_EXITOSA)
            .operacionObservaciones(UPDATED_OPERACION_OBSERVACIONES)
            .ejecutada(UPDATED_EJECUTADA)
            .reportada(UPDATED_REPORTADA);
        return orden;
    }

    @BeforeEach
    public void initTest() {
        orden = createEntity(em);
    }

    @Test
    @Transactional
    void createOrden() throws Exception {
        int databaseSizeBeforeCreate = ordenRepository.findAll().size();
        // Create the Orden
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);
        restOrdenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordenDTO)))
            .andExpect(status().isCreated());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeCreate + 1);
        Orden testOrden = ordenList.get(ordenList.size() - 1);
        assertThat(testOrden.getClienteId()).isEqualTo(DEFAULT_CLIENTE_ID);
        assertThat(testOrden.getAccionId()).isEqualTo(DEFAULT_ACCION_ID);
        assertThat(testOrden.getCodigoAccion()).isEqualTo(DEFAULT_CODIGO_ACCION);
        assertThat(testOrden.getOperacion()).isEqualTo(DEFAULT_OPERACION);
        assertThat(testOrden.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testOrden.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testOrden.getFechaOperacion()).isEqualTo(DEFAULT_FECHA_OPERACION);
        assertThat(testOrden.getModo()).isEqualTo(DEFAULT_MODO);
        assertThat(testOrden.getOperacionExitosa()).isEqualTo(DEFAULT_OPERACION_EXITOSA);
        assertThat(testOrden.getOperacionObservaciones()).isEqualTo(DEFAULT_OPERACION_OBSERVACIONES);
        assertThat(testOrden.getEjecutada()).isEqualTo(DEFAULT_EJECUTADA);
        assertThat(testOrden.getReportada()).isEqualTo(DEFAULT_REPORTADA);
    }

    @Test
    @Transactional
    void createOrdenWithExistingId() throws Exception {
        // Create the Orden with an existing ID
        orden.setId(1L);
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);

        int databaseSizeBeforeCreate = ordenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdens() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get all the ordenList
        restOrdenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orden.getId().intValue())))
            .andExpect(jsonPath("$.[*].clienteId").value(hasItem(DEFAULT_CLIENTE_ID)))
            .andExpect(jsonPath("$.[*].accionId").value(hasItem(DEFAULT_ACCION_ID)))
            .andExpect(jsonPath("$.[*].codigoAccion").value(hasItem(DEFAULT_CODIGO_ACCION)))
            .andExpect(jsonPath("$.[*].operacion").value(hasItem(DEFAULT_OPERACION)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].fechaOperacion").value(hasItem(DEFAULT_FECHA_OPERACION.toString())))
            .andExpect(jsonPath("$.[*].modo").value(hasItem(DEFAULT_MODO)))
            .andExpect(jsonPath("$.[*].operacionExitosa").value(hasItem(DEFAULT_OPERACION_EXITOSA.booleanValue())))
            .andExpect(jsonPath("$.[*].operacionObservaciones").value(hasItem(DEFAULT_OPERACION_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].ejecutada").value(hasItem(DEFAULT_EJECUTADA.booleanValue())))
            .andExpect(jsonPath("$.[*].reportada").value(hasItem(DEFAULT_REPORTADA.booleanValue())));
    }

    @Test
    @Transactional
    void getOrden() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get the orden
        restOrdenMockMvc
            .perform(get(ENTITY_API_URL_ID, orden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orden.getId().intValue()))
            .andExpect(jsonPath("$.clienteId").value(DEFAULT_CLIENTE_ID))
            .andExpect(jsonPath("$.accionId").value(DEFAULT_ACCION_ID))
            .andExpect(jsonPath("$.codigoAccion").value(DEFAULT_CODIGO_ACCION))
            .andExpect(jsonPath("$.operacion").value(DEFAULT_OPERACION))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.fechaOperacion").value(DEFAULT_FECHA_OPERACION.toString()))
            .andExpect(jsonPath("$.modo").value(DEFAULT_MODO))
            .andExpect(jsonPath("$.operacionExitosa").value(DEFAULT_OPERACION_EXITOSA.booleanValue()))
            .andExpect(jsonPath("$.operacionObservaciones").value(DEFAULT_OPERACION_OBSERVACIONES))
            .andExpect(jsonPath("$.ejecutada").value(DEFAULT_EJECUTADA.booleanValue()))
            .andExpect(jsonPath("$.reportada").value(DEFAULT_REPORTADA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingOrden() throws Exception {
        // Get the orden
        restOrdenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrden() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();

        // Update the orden
        Orden updatedOrden = ordenRepository.findById(orden.getId()).get();
        // Disconnect from session so that the updates on updatedOrden are not directly saved in db
        em.detach(updatedOrden);
        updatedOrden
            .clienteId(UPDATED_CLIENTE_ID)
            .accionId(UPDATED_ACCION_ID)
            .codigoAccion(UPDATED_CODIGO_ACCION)
            .operacion(UPDATED_OPERACION)
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD)
            .fechaOperacion(UPDATED_FECHA_OPERACION)
            .modo(UPDATED_MODO)
            .operacionExitosa(UPDATED_OPERACION_EXITOSA)
            .operacionObservaciones(UPDATED_OPERACION_OBSERVACIONES)
            .ejecutada(UPDATED_EJECUTADA)
            .reportada(UPDATED_REPORTADA);
        OrdenDTO ordenDTO = ordenMapper.toDto(updatedOrden);

        restOrdenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordenDTO))
            )
            .andExpect(status().isOk());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
        Orden testOrden = ordenList.get(ordenList.size() - 1);
        assertThat(testOrden.getClienteId()).isEqualTo(UPDATED_CLIENTE_ID);
        assertThat(testOrden.getAccionId()).isEqualTo(UPDATED_ACCION_ID);
        assertThat(testOrden.getCodigoAccion()).isEqualTo(UPDATED_CODIGO_ACCION);
        assertThat(testOrden.getOperacion()).isEqualTo(UPDATED_OPERACION);
        assertThat(testOrden.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testOrden.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testOrden.getFechaOperacion()).isEqualTo(UPDATED_FECHA_OPERACION);
        assertThat(testOrden.getModo()).isEqualTo(UPDATED_MODO);
        assertThat(testOrden.getOperacionExitosa()).isEqualTo(UPDATED_OPERACION_EXITOSA);
        assertThat(testOrden.getOperacionObservaciones()).isEqualTo(UPDATED_OPERACION_OBSERVACIONES);
        assertThat(testOrden.getEjecutada()).isEqualTo(UPDATED_EJECUTADA);
        assertThat(testOrden.getReportada()).isEqualTo(UPDATED_REPORTADA);
    }

    @Test
    @Transactional
    void putNonExistingOrden() throws Exception {
        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();
        orden.setId(count.incrementAndGet());

        // Create the Orden
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrden() throws Exception {
        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();
        orden.setId(count.incrementAndGet());

        // Create the Orden
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrden() throws Exception {
        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();
        orden.setId(count.incrementAndGet());

        // Create the Orden
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdenWithPatch() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();

        // Update the orden using partial update
        Orden partialUpdatedOrden = new Orden();
        partialUpdatedOrden.setId(orden.getId());

        partialUpdatedOrden
            .operacion(UPDATED_OPERACION)
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD)
            .modo(UPDATED_MODO)
            .operacionObservaciones(UPDATED_OPERACION_OBSERVACIONES)
            .reportada(UPDATED_REPORTADA);

        restOrdenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrden.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrden))
            )
            .andExpect(status().isOk());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
        Orden testOrden = ordenList.get(ordenList.size() - 1);
        assertThat(testOrden.getClienteId()).isEqualTo(DEFAULT_CLIENTE_ID);
        assertThat(testOrden.getAccionId()).isEqualTo(DEFAULT_ACCION_ID);
        assertThat(testOrden.getCodigoAccion()).isEqualTo(DEFAULT_CODIGO_ACCION);
        assertThat(testOrden.getOperacion()).isEqualTo(UPDATED_OPERACION);
        assertThat(testOrden.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testOrden.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testOrden.getFechaOperacion()).isEqualTo(DEFAULT_FECHA_OPERACION);
        assertThat(testOrden.getModo()).isEqualTo(UPDATED_MODO);
        assertThat(testOrden.getOperacionExitosa()).isEqualTo(DEFAULT_OPERACION_EXITOSA);
        assertThat(testOrden.getOperacionObservaciones()).isEqualTo(UPDATED_OPERACION_OBSERVACIONES);
        assertThat(testOrden.getEjecutada()).isEqualTo(DEFAULT_EJECUTADA);
        assertThat(testOrden.getReportada()).isEqualTo(UPDATED_REPORTADA);
    }

    @Test
    @Transactional
    void fullUpdateOrdenWithPatch() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();

        // Update the orden using partial update
        Orden partialUpdatedOrden = new Orden();
        partialUpdatedOrden.setId(orden.getId());

        partialUpdatedOrden
            .clienteId(UPDATED_CLIENTE_ID)
            .accionId(UPDATED_ACCION_ID)
            .codigoAccion(UPDATED_CODIGO_ACCION)
            .operacion(UPDATED_OPERACION)
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD)
            .fechaOperacion(UPDATED_FECHA_OPERACION)
            .modo(UPDATED_MODO)
            .operacionExitosa(UPDATED_OPERACION_EXITOSA)
            .operacionObservaciones(UPDATED_OPERACION_OBSERVACIONES)
            .ejecutada(UPDATED_EJECUTADA)
            .reportada(UPDATED_REPORTADA);

        restOrdenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrden.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrden))
            )
            .andExpect(status().isOk());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
        Orden testOrden = ordenList.get(ordenList.size() - 1);
        assertThat(testOrden.getClienteId()).isEqualTo(UPDATED_CLIENTE_ID);
        assertThat(testOrden.getAccionId()).isEqualTo(UPDATED_ACCION_ID);
        assertThat(testOrden.getCodigoAccion()).isEqualTo(UPDATED_CODIGO_ACCION);
        assertThat(testOrden.getOperacion()).isEqualTo(UPDATED_OPERACION);
        assertThat(testOrden.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testOrden.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testOrden.getFechaOperacion()).isEqualTo(UPDATED_FECHA_OPERACION);
        assertThat(testOrden.getModo()).isEqualTo(UPDATED_MODO);
        assertThat(testOrden.getOperacionExitosa()).isEqualTo(UPDATED_OPERACION_EXITOSA);
        assertThat(testOrden.getOperacionObservaciones()).isEqualTo(UPDATED_OPERACION_OBSERVACIONES);
        assertThat(testOrden.getEjecutada()).isEqualTo(UPDATED_EJECUTADA);
        assertThat(testOrden.getReportada()).isEqualTo(UPDATED_REPORTADA);
    }

    @Test
    @Transactional
    void patchNonExistingOrden() throws Exception {
        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();
        orden.setId(count.incrementAndGet());

        // Create the Orden
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrden() throws Exception {
        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();
        orden.setId(count.incrementAndGet());

        // Create the Orden
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrden() throws Exception {
        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();
        orden.setId(count.incrementAndGet());

        // Create the Orden
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ordenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrden() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        int databaseSizeBeforeDelete = ordenRepository.findAll().size();

        // Delete the orden
        restOrdenMockMvc
            .perform(delete(ENTITY_API_URL_ID, orden.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
