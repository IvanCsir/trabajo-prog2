package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.service.ColaFinDiaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ColaFinDiaServiceImpl implements ColaFinDiaService {

    private final Logger log = LoggerFactory.getLogger(ColaFinDiaServiceImpl.class);
}
