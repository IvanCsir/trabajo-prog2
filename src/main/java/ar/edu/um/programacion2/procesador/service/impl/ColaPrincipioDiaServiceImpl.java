package ar.edu.um.programacion2.procesador.service.impl;

import ar.edu.um.programacion2.procesador.service.ColaPrincipioDiaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ColaPrincipioDiaServiceImpl implements ColaPrincipioDiaService {

    private final Logger log = LoggerFactory.getLogger(ColaPrincipioDiaServiceImpl.class);
}
