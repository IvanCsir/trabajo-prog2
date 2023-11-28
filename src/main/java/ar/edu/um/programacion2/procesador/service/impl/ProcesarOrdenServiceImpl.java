package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.service.ProcesarOrdenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcesarOrdenServiceImpl implements ProcesarOrdenService {

    private final Logger log = LoggerFactory.getLogger(ProcesarOrdenServiceImpl.class);

    @Override
    public boolean comprar(Orden orden) {
        orden.setEjecutada(true);
        orden.setOperacionExitosa(true);
        orden.setOperacionObservaciones("Compra realizada con éxito");
        log.info("COMPRA REALIZADA CON EXITO ORDEN ID: {}", orden.getId());
        return true;
    }

    @Override
    public boolean vender(Orden orden) {
        orden.setEjecutada(true);
        orden.setOperacionExitosa(true);
        orden.setOperacionObservaciones("Venta realizada con éxito");
        log.info("VENTA REALIZADA CON EXITO ORDEN ID: {}", orden.getId());
        return true;
    }

    @Override
    public Orden procesarOrden(String modo) {
        return null;
    }
}
