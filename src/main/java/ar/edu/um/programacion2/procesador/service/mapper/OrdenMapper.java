package ar.edu.um.programacion2.procesador.service.mapper;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.service.dto.OrdenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orden} and its DTO {@link OrdenDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrdenMapper extends EntityMapper<OrdenDTO, Orden> {}
