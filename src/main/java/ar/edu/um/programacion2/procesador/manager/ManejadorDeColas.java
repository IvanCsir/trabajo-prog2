package ar.edu.um.programacion2.procesador.manager;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.service.impl.RepartidorOrdenesAColasServiceImpl;
import java.util.LinkedList;
import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ManejadorDeColas {

    private final Logger log = LoggerFactory.getLogger(RepartidorOrdenesAColasServiceImpl.class);

    private final Queue<Orden> colaInmediatas = new LinkedList<>();
    private final Queue<Orden> colaPrincipioDia = new LinkedList<>();
    private final Queue<Orden> colaFinDia = new LinkedList<>();

    public Queue<Orden> getColaInmediatas() {
        return colaInmediatas;
    }

    public Queue<Orden> getColaPrincipioDia() {
        return colaPrincipioDia;
    }

    public Queue<Orden> getColaFinDia() {
        return colaFinDia;
    }

    public void agregarOrdenInmediata(Orden orden) {
        //colaInmediatas.offer(orden);
        if (!contieneOrdenPorId(colaInmediatas, orden.getId())) {
            colaInmediatas.offer(orden);
            log.info("ORDEN ID:{} AGREGADA A COLA INMEDIATAS ", orden.getId());
        } else {
            log.info("ORDEN ID:{} YA SE ENCUENTRA EN LA COLA DE INMEDIATAS ", orden.getId());
        }
    }

    public Orden desencolarInmediata() {
        return colaInmediatas.poll();
    }

    public void agregarOrdenPrincipioDia(Orden orden) {
        //colaPrincipioDia.offer(orden);
        if (!contieneOrdenPorId(colaPrincipioDia, orden.getId())) {
            colaPrincipioDia.offer(orden);
            log.info("ORDEN ID:{} AGREGADA A COLA PRINCIPIO DIA ", orden.getId());
        } else {
            log.info("ORDEN ID:{} YA SE ENCUENTRA EN LA COLA DE PRINCIPIO DIA ", orden.getId());
        }
    }

    public Orden desencolarPrincipioDia() {
        return colaPrincipioDia.poll();
    }

    public void agregarOrdenFinDia(Orden orden) {
        //colaFinDia.offer(orden);
        if (!contieneOrdenPorId(colaFinDia, orden.getId())) {
            colaFinDia.offer(orden);
            log.info("ORDEN ID:{} AGREGADA A COLA FIN DIA ", orden.getId());
        } else {
            log.info("ORDEN ID:{} YA SE ENCUENTRA EN LA COLA DE FIN DIA ", orden.getId());
        }
    }

    public Orden desencolarFinDia() {
        return colaFinDia.poll();
    }

    private boolean contieneOrdenPorId(Queue<Orden> cola, Long id) {
        return cola.stream().anyMatch(o -> o.getId().equals(id));
    }
}
