package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.service.ColaInmediatasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ColaInmediatasServiceImpl implements ColaInmediatasService {

    private final Logger log = LoggerFactory.getLogger(ColaInmediatasServiceImpl.class);
}
