package ar.edu.um.programacion2.procesador.repository;

import ar.edu.um.programacion2.procesador.domain.Orden;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Orden entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByModo(String modo);
    List<Orden> findByModoAndEjecutada(String modo, Boolean ejecutada);

    List<Orden> findByEjecutada(Boolean ejecutada);

    List<Orden> findById(int id);
}
