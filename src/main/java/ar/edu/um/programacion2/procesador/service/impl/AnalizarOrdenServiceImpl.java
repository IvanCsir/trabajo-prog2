package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.service.AnalizarOrdenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnalizarOrdenServiceImpl implements AnalizarOrdenService {

    private final Logger log = LoggerFactory.getLogger(AnalizarOrdenServiceImpl.class);

    @Override
    public boolean consultarAccion(String codigo) {
        String token =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdmFuZnJlaWJlcmciLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzMwMzYyNjcwfQ._03byVo-wHkFD1Uq7scw4MHWHl7hlI0B6JmpTKqb2iaIG1V8rEXhsFNEd8Us2NrFWxwkUYQQkEa3k6QcWxBJyQ"; // Reemplaza con tu token real

        HttpClient client = HttpClient.newHttpClient();

        String url = "http://192.168.194.254:8000/api/acciones/buscar?codigo=" + codigo;

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + token).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.body());

            // Obtengo la lista de acciones
            JsonNode acciones = jsonResponse.get("acciones");

            if (acciones != null && acciones.isArray() && acciones.size() > 0) {
                log.info("Acción encontrada para el código '{}'", codigo);
                return true;
            } else {
                log.info("No se encontró ninguna acción para el código '{}'", codigo);
                return false;
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error al consultar la acción");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean consultarCliente(int id) {
        String token =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdmFuZnJlaWJlcmciLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzMwMzYyNjcwfQ._03byVo-wHkFD1Uq7scw4MHWHl7hlI0B6JmpTKqb2iaIG1V8rEXhsFNEd8Us2NrFWxwkUYQQkEa3k6QcWxBJyQ"; // Reemplaza con tu token real

        HttpClient client = HttpClient.newHttpClient();

        String url = "http://192.168.194.254:8000/api/clientes/";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + token).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            //System.out.println(jsonResponse);

            // Obtengo la lista de clientes
            JsonNode clientes = jsonResponse.get("clientes");

            if (clientes != null && clientes.isArray()) {
                for (JsonNode cliente : clientes) {
                    int idCliente = cliente.get("id").asInt();
                    if (id == idCliente) {
                        String nombreApellido = cliente.get("nombreApellido").asText();
                        log.info("Cliente con ID {} encontrado: {}", id, nombreApellido);
                        return true;
                    }
                }
            }

            log.info("Cliente con ID {} no encontrado", id);
            return false;
        } catch (IOException | InterruptedException e) {
            log.error("Error al consultar el cliente", e);
            return false;
        }
    }

    @Override
    public boolean consultarHora(Instant fechaOperacion) {
        ZoneId zonaHorariaUtcMenos3 = ZoneId.of("UTC-3");

        // Convertir la fechaOperacion a ZonedDateTime en la zona horaria UTC-3
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(fechaOperacion, zonaHorariaUtcMenos3);

        // Obtener la hora actual en la zona horaria UTC-3
        LocalDateTime horaActualUtcMenos3 = ZonedDateTime.now(zonaHorariaUtcMenos3).toLocalDateTime();

        LocalDateTime limiteInicio = horaActualUtcMenos3.withHour(9).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime limiteFin = horaActualUtcMenos3.withHour(18).withMinute(0).withSecond(0).withNano(0);

        //boolean resultado = true;
        boolean resultado =
            !fechaOperacion.isBefore(limiteInicio.atZone(zonaHorariaUtcMenos3).toInstant()) &&
            fechaOperacion.isBefore(limiteFin.atZone(zonaHorariaUtcMenos3).toInstant());
        if (!resultado) {
            log.info("La fecha operación {} no está entre las 9:00 hs y 18:00 hs", fechaOperacion);
        } else {
            log.info("La fecha operación {} está entre las 9:00 hs y 18:00 hs", fechaOperacion);
        }
        // Verificar si la fechaOperacion está entre los límites
        return resultado;
    }
}