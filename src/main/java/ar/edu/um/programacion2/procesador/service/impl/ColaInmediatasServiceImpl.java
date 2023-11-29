package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.manager.ManejadorDeColas;
import ar.edu.um.programacion2.procesador.repository.OrdenRepository;
import ar.edu.um.programacion2.procesador.service.AnalizarOrdenService;
import ar.edu.um.programacion2.procesador.service.ColaInmediatasService;
import ar.edu.um.programacion2.procesador.service.ProcesarOrdenService;
import ar.edu.um.programacion2.procesador.service.ReportarOrdenService;
import com.netflix.discovery.converters.Auto;
import java.util.List;
import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ColaInmediatasServiceImpl implements ColaInmediatasService {

    private final Logger log = LoggerFactory.getLogger(ColaInmediatasServiceImpl.class);

    @Autowired
    protected ProcesarOrdenService procesarOrdenService;

    @Autowired
    protected ManejadorDeColas manejadorDeColas;

    @Autowired
    protected AnalizarOrdenService analizarOrdenService;

    @Autowired
    protected OrdenRepository ordenRepository;

    @Autowired
    protected ReportarOrdenService reportarOrdenService;

    @Override
    public boolean procesarOrdenesInmediatas() {
        Queue<Orden> cola = manejadorDeColas.getColaInmediatas();
        log.info("Tamaño inicial de la cola: {}", cola.size());
        while (!cola.isEmpty()) {
            Orden orden = cola.poll();
            if (analizarOrdenService.consultarHora(orden.getFechaOperacion())) {
                System.out.println(orden.getFechaOperacion());
                if (
                    analizarOrdenService.consultarAccion(orden.getCodigoAccion()) &&
                    analizarOrdenService.consultarCliente(orden.getClienteId())
                ) {
                    if (orden.getOperacion().equals("COMPRA")) {
                        procesarOrdenService.comprar(orden);
                        log.info("COMPRA REALIZADA CON ÉXITO ORDEN ID: {}", orden.getId());
                        ordenRepository.save(orden);
                        reportarOrdenService.reportarOrden(orden);
                    } else {
                        procesarOrdenService.vender(orden);
                        log.info("VENTA REALIZADA CON ÉXITO ORDEN ID:{}", orden.getId());
                        ordenRepository.save(orden);
                        reportarOrdenService.reportarOrden(orden);
                    }
                } else {
                    log.info("ORDEN ID:{} NO PROCESADA POR ACCIÓN O CLIENTE INEXISTENTE", orden.getId());
                    orden.setEjecutada(true);
                    orden.setOperacionExitosa(false);
                    orden.setOperacionObservaciones("Cliente o accion inexistente");
                    ordenRepository.save(orden);
                    reportarOrdenService.reportarOrden(orden);
                    return false;
                }
            } else {
                log.info("FUERA DE HORARIO DE COMPRA Y VENTA DE ACCIONES");
                orden.setEjecutada(true);
                orden.setOperacionExitosa(false);
                orden.setOperacionObservaciones("Fuera del horario de compra y venta de acciones");
                ordenRepository.save(orden);
                reportarOrdenService.reportarOrden(orden);
                return false;
            }
        }
        log.info("COLA DE ORDENES INMEDIATAS VACiA");
        return false;
    }

    @Scheduled(fixedRate = 30000) // Lo ejecuto cada 1 minuto y medio
    public boolean procesarOrd() {
        boolean resultado = procesarOrdenesInmediatas();
        return resultado;
    }
}
