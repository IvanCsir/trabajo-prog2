package ar.edu.um.programacion2.procesador.service;

import ar.edu.um.programacion2.procesador.domain.Orden;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface ReportarOrdenService {
    public boolean reportarOrdenes(List<Orden> lista_ordenes);

    public List<Orden> getReportesByCliente(int id);

    public List<Orden> getReportesByCodigoAccion(String codigo_accion);

    public List<Orden> getReportesByFechaOperacionBetween(Instant inicioDia, Instant finDia);
}
