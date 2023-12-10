package ar.edu.um.programacion2.procesador.web.rest;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.repository.OrdenRepository;
import ar.edu.um.programacion2.procesador.repository.ReportarOrdenRepository;
import ar.edu.um.programacion2.procesador.service.ReportarOrdenService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FiltrarReportesResource controller
 */
@RestController
@RequestMapping("/api/filtrar-reportes")
public class FiltrarReportesResource {

    @Autowired
    protected ReportarOrdenService reportarOrdenService;

    private final Logger log = LoggerFactory.getLogger(FiltrarReportesResource.class);

    /**
     * GET defaultAction
     */
    @GetMapping("/cliente/{id}")
    public List<Orden> filtrarPorCliente(@PathVariable int id) {
        return reportarOrdenService.getReportesByCliente(id);
    }

    @GetMapping("/accion/{codigo_accion}")
    public List<Orden> filtrarPorCliente(@PathVariable String codigo_accion) {
        codigo_accion = codigo_accion.toUpperCase();
        return reportarOrdenService.getReportesByCodigoAccion(codigo_accion);
    }

    @GetMapping("/fecha/{fecha}")
    public List<Orden> filtrarPorFecha(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
        Instant inicioDia = fecha.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant finDia = fecha.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant();
        return reportarOrdenService.getReportesByFechaOperacionBetween(inicioDia, finDia);
    }
}
