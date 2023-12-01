package ar.edu.um.programacion2.procesador.service;

import ar.edu.um.programacion2.procesador.domain.Orden;
import java.util.List;

public interface ReportarOrdenService {
    public boolean reportarOrdenes(List<Orden> lista_ordenes);
}
