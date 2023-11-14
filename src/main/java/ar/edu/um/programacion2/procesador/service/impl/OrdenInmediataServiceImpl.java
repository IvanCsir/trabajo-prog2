package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.domain.Orden;
import ar.edu.um.programacion2.procesador.repository.OrdenRepository;
import ar.edu.um.programacion2.procesador.service.OrdenInmediataService;
import java.net.http.HttpResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrdenInmediataServiceImpl implements OrdenInmediataService {

    private final Logger log = LoggerFactory.getLogger(OrdenInmediataServiceImpl.class);

    @Autowired
    protected OrdenRepository ordenRepository;

    @Override
    public List<Orden> procesarOrdenesAhora() {
        List<Orden> lista_ordenes = this.ordenRepository.findByModoAndEjecutada("AHORA", false);
        System.out.println(lista_ordenes);
        return lista_ordenes;
    }
}
