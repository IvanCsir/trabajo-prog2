import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './orden.reducer';

export const OrdenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ordenEntity = useAppSelector(state => state.orden.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ordenDetailsHeading">Orden</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ordenEntity.id}</dd>
          <dt>
            <span id="clienteId">Cliente Id</span>
          </dt>
          <dd>{ordenEntity.clienteId}</dd>
          <dt>
            <span id="accionId">Accion Id</span>
          </dt>
          <dd>{ordenEntity.accionId}</dd>
          <dt>
            <span id="codigoAccion">Codigo Accion</span>
          </dt>
          <dd>{ordenEntity.codigoAccion}</dd>
          <dt>
            <span id="operacion">Operacion</span>
          </dt>
          <dd>{ordenEntity.operacion}</dd>
          <dt>
            <span id="precio">Precio</span>
          </dt>
          <dd>{ordenEntity.precio}</dd>
          <dt>
            <span id="cantidad">Cantidad</span>
          </dt>
          <dd>{ordenEntity.cantidad}</dd>
          <dt>
            <span id="fechaOperacion">Fecha Operacion</span>
          </dt>
          <dd>
            {ordenEntity.fechaOperacion ? <TextFormat value={ordenEntity.fechaOperacion} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="modo">Modo</span>
          </dt>
          <dd>{ordenEntity.modo}</dd>
          <dt>
            <span id="operacionExitosa">Operacion Exitosa</span>
          </dt>
          <dd>{ordenEntity.operacionExitosa ? 'true' : 'false'}</dd>
          <dt>
            <span id="operacionObservaciones">Operacion Observaciones</span>
          </dt>
          <dd>{ordenEntity.operacionObservaciones}</dd>
          <dt>
            <span id="ejecutada">Ejecutada</span>
          </dt>
          <dd>{ordenEntity.ejecutada ? 'true' : 'false'}</dd>
          <dt>
            <span id="reportada">Reportada</span>
          </dt>
          <dd>{ordenEntity.reportada ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/orden" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/orden/${ordenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrdenDetail;
