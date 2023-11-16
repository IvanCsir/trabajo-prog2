package ar.edu.um.programacion2.procesador;

import ar.edu.um.programacion2.procesador.service.AnalizarOrdenService;
import ar.edu.um.programacion2.procesador.service.impl.AnalizarOrdenServiceImpl;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Main {

    public static void main(String[] args) {
        String codigoPrueba = "asdalksndkja";

        AnalizarOrdenService ordenService = new AnalizarOrdenServiceImpl(); // Reemplaza con la instancia adecuada

        //boolean resultadoConsulta = ordenService.consultarAccion(codigoPrueba);
        //boolean res = ordenService.consultarCliente(1139);

        Instant hora = Instant.parse("2023-11-16T04:00:00Z");
        ZoneId zonaHorariaUtcMenos3 = ZoneId.of("UTC-3");
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(hora, zonaHorariaUtcMenos3);

        boolean ress = ordenService.consultarHora(zonedDateTime.toInstant());
    }
}
