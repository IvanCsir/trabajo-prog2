package ar.edu.um.programacion2.procesador.service;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.service.dto.OrdenDTO;
import java.time.Instant;

public interface AnalizarOrdenService {
    public boolean consultarAccion(String codigo);

    public boolean consultarCliente(int id);

    public boolean consultarHora(Instant fechaOperacion);

    public Orden actualizarPrecio(Orden orden);
}
