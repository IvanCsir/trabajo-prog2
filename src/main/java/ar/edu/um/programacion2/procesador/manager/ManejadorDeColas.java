package ar.edu.um.programacion2.procesador.manager;

import ar.edu.um.programacion2.procesador.domain.Orden;
import java.util.LinkedList;
import java.util.Queue;
import org.springframework.stereotype.Component;

@Component
public class ManejadorDeColas {

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
        colaInmediatas.offer(orden);
    }

    public Orden desencolarInmediata() {
        return colaInmediatas.poll();
    }

    public void agregarOrdenPrincipioDia(Orden orden) {
        colaPrincipioDia.offer(orden);
    }

    public Orden desencolarPrincipioDia() {
        return colaPrincipioDia.poll();
    }

    public void agregarOrdenFinDia(Orden orden) {
        colaFinDia.offer(orden);
    }

    public Orden desencolarFinDia() {
        return colaFinDia.poll();
    }
}
