package ar.edu.um.programacion2.procesador.repository;

import ar.edu.um.programacion2.procesador.domain.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObtenerOrdenesCatedraRepository extends JpaRepository<Orden, Long> {}
