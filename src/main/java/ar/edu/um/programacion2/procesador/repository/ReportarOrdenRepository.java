package ar.edu.um.programacion2.procesador.repository;

import ar.edu.um.programacion2.procesador.domain.Orden;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportarOrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByReportadaAndClienteId(Boolean reportada, int id);

    List<Orden> findByReportadaAndCodigoAccion(Boolean reportada, String codigo_accion);

    List<Orden> findByReportadaAndFechaOperacionBetween(Boolean reportada, Instant inicioDia, Instant finDia);
}
