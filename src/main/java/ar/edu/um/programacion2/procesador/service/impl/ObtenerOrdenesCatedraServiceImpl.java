package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.manager.ManejadorDeColas;
import ar.edu.um.programacion2.procesador.repository.ObtenerOrdenesCatedraRepository;
import ar.edu.um.programacion2.procesador.service.ObtenerOrdenesCatedraService;
import ar.edu.um.programacion2.procesador.service.dto.OrdenesDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix.discovery.converters.Auto;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerOrdenesCatedraServiceImpl implements ObtenerOrdenesCatedraService {

    private final Logger log = LoggerFactory.getLogger(ObtenerOrdenesCatedraServiceImpl.class);

    @Autowired
    ObtenerOrdenesCatedraRepository obtenerOrdenesCatedraRepository;

    @Autowired
    public ManejadorDeColas manejadorDeColas;

    @Override
    public HttpResponse<String> obtenerRespuestaOrdenes() {
        String token =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdmFuZnJlaWJlcmciLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzMwMzYyNjcwfQ._03byVo-wHkFD1Uq7scw4MHWHl7hlI0B6JmpTKqb2iaIG1V8rEXhsFNEd8Us2NrFWxwkUYQQkEa3k6QcWxBJyQ";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        request =
            HttpRequest
                .newBuilder()
                .uri(URI.create("http://192.168.194.254:8000/api/ordenes/ordenes"))
                .header("Authorization", "Bearer " + token)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("ORDENES OBTENIDAS CORRECTAMENTE DEL SERVICIO CATEDRA");
            return response;
        } catch (IOException | InterruptedException e) {
            log.error("NO SE PUDIERON OBTENER LAS ORDENES DEL SERVICIO CATEDRA");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Orden> mapeoOrdenesCatedraToOrdenesDTO(HttpResponse<String> jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            OrdenesDTO response = objectMapper.readValue(jsonResponse.body(), OrdenesDTO.class);
            List<Orden> ordenes = response.getOrdenes();

            List<Orden> ordenesGuardadas = obtenerOrdenesCatedraRepository.findAll();

            for (Orden orden : ordenes) {
                boolean ordenExistente = ordenesGuardadas
                    .stream()
                    .anyMatch(ord -> ord.getFechaOperacion().equals(orden.getFechaOperacion()));
                if (!ordenExistente) {
                    orden.setReportada(false);
                    orden.setEjecutada(false);
                    orden.setOperacionExitosa(false);
                    orden.setOperacionObservaciones("");
                    this.obtenerOrdenesCatedraRepository.save(orden);
                }
            }

            return ordenes;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Scheduled(fixedRate = 60000) // Lo ejecuto cada 1 minuto
    public List<Orden> obtenerOrdenesScheduled() {
        HttpResponse<String> response = obtenerRespuestaOrdenes();
        List<Orden> lista_ordenes = mapeoOrdenesCatedraToOrdenesDTO(response);
        System.out.println(lista_ordenes);
        return lista_ordenes;
    }
    /* @Scheduled(fixedRate = 60001)
    public void probandoRepartidorColas(){
        RepartidorOrdenesAColasServiceImpl prueba = new RepartidorOrdenesAColasServiceImpl();
        prueba.analizarModo();
        System.out.println("Ordenes in" + prueba.manejadorDeColas.getColaInmediatas());
        System.out.println("Ordenes prin" + prueba.manejadorDeColas.getColaPrincipioDia());
        System.out.println("Ordenes fin" + prueba.manejadorDeColas.getColaFinDia());

    }*/
}
