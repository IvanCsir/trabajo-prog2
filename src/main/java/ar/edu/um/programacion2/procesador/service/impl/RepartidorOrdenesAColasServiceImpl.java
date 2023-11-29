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
                if (orden.getModo().equals("AHORA")) {
                    manejadorDeColas.agregarOrdenInmediata(orden);
                    System.out.println(manejadorDeColas.getColaInmediatas());
                } else if (orden.getModo().equals("PRINCIPIODIA")) {
                    manejadorDeColas.agregarOrdenPrincipioDia(orden);
                    System.out.println(manejadorDeColas.getColaPrincipioDia());
                } else if (orden.getModo().equals("FINDIA")) {
                    manejadorDeColas.agregarOrdenFinDia(orden);
                    System.out.println(manejadorDeColas.getColaFinDia());
                } else {
                    log.warn("Modo no reconocido: {}", orden.getModo());
                }
            }
            return "Ordenes guardadas en colas";
        } catch (Exception e) {
            log.error("Error al agregar Orden a X cola", e);
        }
        return "No se pudieron guardar las ordenes";
    }

    @Scheduled(fixedRate = 60001) // Lo ejecuto cada 1 minuto y medio
    public String ejecutadorRepartidorPorModo() {
        String probando = repartidorPorModo();
        return probando;
    }
}
