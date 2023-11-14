package ar.edu.um.programacion2.procesador.service;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.service.dto.OrdenDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.edu.um.programacion2.procesador.domain.Orden}.
 */
public interface OrdenService {
    /**
     * Save a orden.
     *
     * @param ordenDTO the entity to save.
     * @return the persisted entity.
     */
    OrdenDTO save(OrdenDTO ordenDTO);

    /**
     * Updates a orden.
     *
     * @param ordenDTO the entity to update.
     * @return the persisted entity.
     */
    OrdenDTO update(OrdenDTO ordenDTO);

    /**
     * Partially updates a orden.
     *
     * @param ordenDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdenDTO> partialUpdate(OrdenDTO ordenDTO);

    /**
     * Get all the ordens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrdenDTO> findAll(Pageable pageable);

    /**
     * Get the "id" orden.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdenDTO> findOne(Long id);

    /**
     * Delete the "id" orden.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
