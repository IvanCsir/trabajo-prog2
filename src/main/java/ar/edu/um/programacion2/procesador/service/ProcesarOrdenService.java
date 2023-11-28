package ar.edu.um.programacion2.procesador.service;

import ar.edu.um.programacion2.procesador.domain.Orden;

public interface ProcesarOrdenService {
    public boolean comprar(Orden orden);

    public boolean vender(Orden orden);

    public Orden procesarOrden(String modo);
}
