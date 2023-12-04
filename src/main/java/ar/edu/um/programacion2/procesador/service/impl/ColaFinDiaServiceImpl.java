package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.manager.ManejadorDeColas;
import ar.edu.um.programacion2.procesador.repository.OrdenRepository;
import ar.edu.um.programacion2.procesador.service.AnalizarOrdenService;
import ar.edu.um.programacion2.procesador.service.ColaFinDiaService;
import ar.edu.um.programacion2.procesador.service.ProcesarOrdenService;
import ar.edu.um.programacion2.procesador.service.ReportarOrdenService;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ColaFinDiaServiceImpl implements ColaFinDiaService {

    private final Logger log = LoggerFactory.getLogger(ColaFinDiaServiceImpl.class);

    @Autowired
    protected ManejadorDeColas manejadorDeColas;

    @Autowired
    protected AnalizarOrdenService analizarOrdenService;

    @Autowired
    protected ProcesarOrdenService procesarOrdenService;

    @Autowired
    protected OrdenRepository ordenRepository;

    @Autowired
    protected ReportarOrdenService reportarOrdenService;

    @Override
    public boolean procesarOrdenesFinDia() {
        Queue<Orden> cola = manejadorDeColas.getColaFinDia();
        List<Orden> lista_ordenes_reporte = new ArrayList<>();
        log.info("Tamaño inicial de la cola: {}", cola.size());
        while (!cola.isEmpty()) {
            Orden orden = cola.poll();
            if (
                analizarOrdenService.consultarAccion(orden.getCodigoAccion()) && analizarOrdenService.consultarCliente(orden.getClienteId())
            ) {
                Orden orden_actualizada = analizarOrdenService.actualizarPrecio(orden);
                if (orden_actualizada.getOperacion().equals("COMPRA")) {
                    procesarOrdenService.comprar(orden_actualizada);
                    log.info("COMPRA REALIZADA CON ÉXITO ORDEN ID: {}", orden_actualizada.getId());
                    ordenRepository.save(orden_actualizada);
                    lista_ordenes_reporte.add(orden_actualizada);
                } else {
                    procesarOrdenService.vender(orden_actualizada);
                    log.info("VENTA REALIZADA CON ÉXITO ORDEN ID:{}", orden_actualizada.getId());
                    ordenRepository.save(orden_actualizada);
                    lista_ordenes_reporte.add(orden_actualizada);
                }
            } else {
                log.info("ORDEN ID:{} NO PROCESADA POR ACCIÓN O CLIENTE INEXISTENTE", orden.getId());
                orden.setEjecutada(true);
                orden.setOperacionExitosa(false);
                orden.setOperacionObservaciones("Cliente o accion inexistente");
                ordenRepository.save(orden);
                lista_ordenes_reporte.add(orden);
            }
        }
        if (!lista_ordenes_reporte.isEmpty()) {
            reportarOrdenService.reportarOrdenes(lista_ordenes_reporte);
            lista_ordenes_reporte.clear();
        }
        log.info("COLA DE ORDENES FIN DÍA VACÍA");
        return false;
    }
    /*@Scheduled(cron = "0 56 17 * * ?")
    public void procesarFinDiaScheduled() {
        procesarOrdenesFinDia();
    }*/

}
