package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.manager.ManejadorDeColas;
import ar.edu.um.programacion2.procesador.repository.OrdenRepository;
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
public class RepartidorOrdenesAColasServiceImpl implements RepartidorOrdenesAColasService {

    private final Logger log = LoggerFactory.getLogger(RepartidorOrdenesAColasServiceImpl.class);

    @Autowired
    protected OrdenRepository ordenRepository;

    @Autowired
    protected ManejadorDeColas manejadorDeColas;

    @Override
    public String repartidorPorModo() {
        try {
            List<Orden> lista_ordenes = this.ordenRepository.findByEjecutada(false);

            log.info("Ordenes a procesar: {}", lista_ordenes);

            for (Orden orden : lista_ordenes) {
                if (orden.getModo().equals("AHORA") && !manejadorDeColas.getColaInmediatas().contains(orden.getId())) {
                    manejadorDeColas.agregarOrdenInmediata(orden);
                    System.out.println(manejadorDeColas.getColaInmediatas());
                    log.info("Orden agregada correctamente a la cola de inmediatas");
                } else if (orden.getModo().equals("PRINCIPIODIA") && !manejadorDeColas.getColaPrincipioDia().contains(orden.getId())) {
                    manejadorDeColas.agregarOrdenPrincipioDia(orden);
                    System.out.println(manejadorDeColas.getColaPrincipioDia());
                    log.info("Orden agregada correctamente a la cola de principio día");
                } else if (orden.getModo().equals("FINDIA") && !manejadorDeColas.getColaFinDia().contains(orden.getId())) {
                    manejadorDeColas.agregarOrdenFinDia(orden);
                    System.out.println(manejadorDeColas.getColaFinDia());
                    log.info("Orden agregada correctamente a la cola de fin día");
                } else {
                    log.warn("La orden con ID {} ya está en la cola. Modo no reconocido: {}", orden.getId(), orden.getModo());
                }
            }
            return "Ordenes guardadas en colas";
        } catch (Exception e) {
            log.error("Error al agregar Orden a X cola", e);
        }
        return "No se pudieron guardar las ordenes";
    }

    @Scheduled(fixedRate = 90000) // Lo ejecuto cada 1 minuto
    public String ejecutadorRepartidorPorModo() {
        String probando = repartidorPorModo();
        return probando;
    }
}
