package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.repository.OrdenRepository;
import ar.edu.um.programacion2.procesador.service.OrdenService;
import ar.edu.um.programacion2.procesador.service.dto.OrdenDTO;
import ar.edu.um.programacion2.procesador.service.mapper.OrdenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Orden}.
 */
@Service
@Transactional
public class OrdenServiceImpl implements OrdenService {

    private final Logger log = LoggerFactory.getLogger(OrdenServiceImpl.class);

    private final OrdenRepository ordenRepository;

    private final OrdenMapper ordenMapper;

    public OrdenServiceImpl(OrdenRepository ordenRepository, OrdenMapper ordenMapper) {
        this.ordenRepository = ordenRepository;
        this.ordenMapper = ordenMapper;
    }

    @Override
    public OrdenDTO save(OrdenDTO ordenDTO) {
        log.debug("Request to save Orden : {}", ordenDTO);
        Orden orden = ordenMapper.toEntity(ordenDTO);
        orden = ordenRepository.save(orden);
        return ordenMapper.toDto(orden);
    }

    @Override
    public OrdenDTO update(OrdenDTO ordenDTO) {
        log.debug("Request to update Orden : {}", ordenDTO);
        Orden orden = ordenMapper.toEntity(ordenDTO);
        orden = ordenRepository.save(orden);
        return ordenMapper.toDto(orden);
    }

    @Override
    public Optional<OrdenDTO> partialUpdate(OrdenDTO ordenDTO) {
        log.debug("Request to partially update Orden : {}", ordenDTO);

        return ordenRepository
            .findById(ordenDTO.getId())
            .map(existingOrden -> {
                ordenMapper.partialUpdate(existingOrden, ordenDTO);

                return existingOrden;
            })
            .map(ordenRepository::save)
            .map(ordenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrdenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ordens");
        return ordenRepository.findAll(pageable).map(ordenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenDTO> findOne(Long id) {
        log.debug("Request to get Orden : {}", id);
        return ordenRepository.findById(id).map(ordenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Orden : {}", id);
        ordenRepository.deleteById(id);
    }
}
