package ar.edu.um.programacion2.procesador.service;

import ar.edu.um.programacion2.procesador.domain.Orden;
import java.net.http.HttpResponse;
import java.util.List;

public interface ObtenerOrdenesCatedraService {
    HttpResponse<String> obtenerRespuestaOrdenes();

    List<Orden> mapeoOrdenesCatedraToOrdenesDTO(HttpResponse<String> jsonResponse);
}
