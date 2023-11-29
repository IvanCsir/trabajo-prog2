package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.repository.OrdenRepository;
import ar.edu.um.programacion2.procesador.service.ReportarOrdenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportarOrdenServiceImpl implements ReportarOrdenService {

    private final Logger log = LoggerFactory.getLogger(ReportarOrdenServiceImpl.class);

    @Autowired
    protected OrdenRepository ordenRepository;

    @Override
    public boolean reportarOrden(Orden orden) {
        String token =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdmFuZnJlaWJlcmciLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzMwMzYyNjcwfQ._03byVo-wHkFD1Uq7scw4MHWHl7hlI0B6JmpTKqb2iaIG1V8rEXhsFNEd8Us2NrFWxwkUYQQkEa3k6QcWxBJyQ";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;

        // Coloca la orden en una lista
        List<Orden> listaOrdenes = new ArrayList<>();
        listaOrdenes.add(orden);

        // Construye el mapa con la clave "ordenes"
        Map<String, List<Orden>> jsonMap = Collections.singletonMap("ordenes", listaOrdenes);

        // Convierte el objeto a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String ordenesJson;
        try {
            ordenesJson = objectMapper.writeValueAsString(jsonMap);
            log.info("Contenido del JSON de la orden: " + ordenesJson);
        } catch (JsonProcessingException e) {
            log.error("Error al convertir la lista de órdenes a JSON");
            e.printStackTrace();
            return false;
        }

        // Construyo la solicitud POST
        request =
            HttpRequest
                .newBuilder()
                .uri(URI.create("http://192.168.194.254:8000/api/reporte-operaciones/reportar"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(ordenesJson))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                log.info("ÓRDEN REPORTADA CORRECTAMENTE");
                orden.setReportada(true);
                ordenRepository.save(orden);
                return true;
            } else {
                log.error("ERROR AL REPORTAR LA ÓRDEN. Código de respuesta: " + response.statusCode());
                log.info("Respuesta del servidor: " + response.body());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            log.error("ERROR AL ENVIAR LA ÓRDEN AL SERVICIO");
            e.printStackTrace();
            return false;
        }
    }
}
