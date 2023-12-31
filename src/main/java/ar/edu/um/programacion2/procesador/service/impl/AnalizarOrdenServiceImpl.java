package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.repository.OrdenRepository;
import ar.edu.um.programacion2.procesador.service.AnalizarOrdenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.*;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnalizarOrdenServiceImpl implements AnalizarOrdenService {

    private final Logger log = LoggerFactory.getLogger(AnalizarOrdenServiceImpl.class);

    @Autowired
    protected OrdenRepository ordenRepository;

    @Value("${procesador_ordenes.token}")
    protected String token;

    @Override
    public boolean consultarAccion(String codigo) {
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
        LocalTime hora = LocalTime.ofInstant(fechaOperacion, ZoneId.of("UTC"));

        LocalTime inicio = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(18, 0);

        boolean resultado = hora.isAfter(inicio) && hora.isBefore(fin);

        if (!resultado) {
            log.info("La fecha operación {} no está entre las 9:00 hs y 18:00 hs", fechaOperacion);
        } else {
            log.info("La fecha operación {} está entre las 9:00 hs y 18:00 hs", fechaOperacion);
        }
        return resultado;
    }

    /* @Override
    public boolean consultarHora(Instant fechaOperacion){
        ZonedDateTime hora = fechaOperacion.atZone(ZoneId.of("UTC-3"));
    }*/

    @Override
    public Orden actualizarPrecio(Orden orden) {
        HttpClient client = HttpClient.newHttpClient();

        String url = "http://192.168.194.254:8000/api/acciones/ultimovalor/" + orden.getCodigoAccion();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + token).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.body());

            JsonNode ultimoValor = jsonResponse.get("ultimoValor");
            if (ultimoValor != null) {
                double nuevoPrecio = ultimoValor.get("valor").asDouble();

                //orden.setPrecio(nuevoPrecio);

                Optional<Orden> optionalOrdenExistente = ordenRepository.findById(orden.getId());

                if (optionalOrdenExistente.isPresent()) {
                    Orden ordenExistente = optionalOrdenExistente.get();

                    ordenExistente.setPrecio(nuevoPrecio);

                    ordenRepository.save(ordenExistente);

                    log.info(
                        "PRECIO ACTUALIZADO ORDEN ID {}. PRECIO ANTERIOR:{} - NUEVO PRECIO:{}",
                        orden.getId(),
                        orden.getPrecio(),
                        ordenExistente.getPrecio()
                    );

                    return ordenExistente;
                } else {
                    log.error(
                        "La orden con ID {} no existe en la base de datos. No se realizará ninguna operación de actualización.",
                        orden.getId()
                    );
                    return null;
                }
            } else {
                log.info("No se encontró el último valor para la acción '{}'", orden.getCodigoAccion());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error al consultar el último valor y actualizar el precio", e);
            return null;
        }
    }

    @Override
    public boolean consultarCantidad(Orden orden) {
        String url =
            "http://192.168.194.254:8000/api/reporte-operaciones/consulta_cliente_accion?clienteId=" +
            orden.getClienteId() +
            "&accionId=" +
            orden.getAccionId();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + token).build();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Proceso la respuesta JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.body());

            JsonNode cantidadActualNode = jsonResponse.get("cantidadActual");

            if (cantidadActualNode == null || cantidadActualNode.isNull()) {
                log.warn("EL CLIENTE NO POSEE ACCIONES PARA VENDER");
                return false;
            }

            int cantidadActual = cantidadActualNode.asInt();

            boolean resultado = orden.getCantidad() <= cantidadActual;

            if (resultado) {
                log.info("La cantidad de la orden es menor o igual a la cantidad actual. ¡Operación permitida!");
            } else {
                log.warn("La cantidad de la orden es mayor a la cantidad actual. ¡Operación no permitida!");
            }

            return resultado;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
