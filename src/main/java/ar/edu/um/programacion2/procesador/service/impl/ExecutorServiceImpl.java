package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.service.ColaInmediatasService;
import ar.edu.um.programacion2.procesador.service.ExecutorService;
import ar.edu.um.programacion2.procesador.service.ObtenerOrdenesCatedraService;
import ar.edu.um.programacion2.procesador.service.RepartidorOrdenesAColasService;
import java.net.http.HttpResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExecutorServiceImpl implements ExecutorService {

    private final Logger log = LoggerFactory.getLogger(ExecutorServiceImpl.class);

    @Autowired
    protected ObtenerOrdenesCatedraService ordenesCatedraService;

    @Autowired
    protected RepartidorOrdenesAColasService repartidorOrdenesAColasService;

    @Autowired
    protected ColaInmediatasService colaInmediatasService;

    @Scheduled(fixedRate = 60000) // Lo ejecuto cada 1 minuto
    public List<Orden> obtenerOrdenesCatedra() {
        HttpResponse<String> response = ordenesCatedraService.obtenerRespuestaOrdenes();
        List<Orden> lista_ordenes = ordenesCatedraService.mapeoOrdenesCatedraToOrdenesDTO(response);
        System.out.println(lista_ordenes);
        return lista_ordenes;
    }

    @Scheduled(fixedRate = 70000) // Lo ejecuto cada 1 minuto y medio
    public String repartidorOrdenes() {
        String rta = repartidorOrdenesAColasService.repartidorPorModo();
        return rta;
    }

    @Scheduled(fixedRate = 40000) // Lo ejecuto cada 1 minuto y medio
    public boolean procesarOrdenesInmediatas() {
        boolean resultado = colaInmediatasService.procesarOrdenesInmediatas();
        return resultado;
    }
}
