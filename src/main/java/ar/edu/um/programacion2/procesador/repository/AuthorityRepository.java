package ar.edu.um.programacion2.procesador.repository;

import ar.edu.um.programacion2.procesador.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
