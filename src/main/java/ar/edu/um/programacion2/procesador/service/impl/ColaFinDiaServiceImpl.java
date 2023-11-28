package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.manager.ManejadorDeColas;
import ar.edu.um.programacion2.procesador.repository.OrdenRepository;
import ar.edu.um.programacion2.procesador.service.AnalizarOrdenService;
import ar.edu.um.programacion2.procesador.service.ColaFinDiaService;
import ar.edu.um.programacion2.procesador.service.ProcesarOrdenService;
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

    @Override
    public boolean procesarOrdenesFinDia() {
        Queue<Orden> cola = manejadorDeColas.getColaFinDia();
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
                } else {
                    procesarOrdenService.vender(orden_actualizada);
                    log.info("VENTA REALIZADA CON ÉXITO ORDEN ID:{}", orden_actualizada.getId());
                    ordenRepository.save(orden_actualizada);
                }
            } else {
                log.info("ORDEN ID:{} NO PROCESADA POR ACCIÓN O CLIENTE INEXISTENTE", orden.getId());
                orden.setEjecutada(true);
                orden.setOperacionExitosa(false);
                orden.setOperacionObservaciones("Cliente o accion inexistente");
                ordenRepository.save(orden);
                return false;
            }
        }
        log.info("COLA DE ORDENES FIN DÍA VACÍA");
        return false;
    }
    /*@Scheduled(cron = "0 56 17 * * ?")
    public void procesarFinDiaScheduled() {
        procesarOrdenesFinDia();
    }*/

}
