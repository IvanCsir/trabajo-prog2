package ar.edu.um.programacion2.procesador.service;

import ar.edu.um.programacion2.procesador.domain.Orden;
import java.util.List;

public interface ExecutorService {
    public List<Orden> obtenerOrdenesCatedra();

    public String repartidorOrdenes();

    public boolean procesarOrdenesInmediatas();
}
