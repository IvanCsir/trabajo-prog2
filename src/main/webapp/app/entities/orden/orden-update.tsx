import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrden } from 'app/shared/model/orden.model';
import { getEntity, updateEntity, createEntity, reset } from './orden.reducer';

export const OrdenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ordenEntity = useAppSelector(state => state.orden.entity);
  const loading = useAppSelector(state => state.orden.loading);
  const updating = useAppSelector(state => state.orden.updating);
  const updateSuccess = useAppSelector(state => state.orden.updateSuccess);

  const handleClose = () => {
    navigate('/orden' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaOperacion = convertDateTimeToServer(values.fechaOperacion);

    const entity = {
      ...ordenEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fechaOperacion: displayDefaultDateTime(),
        }
      : {
          ...ordenEntity,
          fechaOperacion: convertDateTimeFromServer(ordenEntity.fechaOperacion),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="procesadorOrdenesApp.orden.home.createOrEditLabel" data-cy="OrdenCreateUpdateHeading">
            Crear o editar Orden
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="orden-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Cliente Id" id="orden-clienteId" name="clienteId" data-cy="clienteId" type="text" />
              <ValidatedField label="Accion Id" id="orden-accionId" name="accionId" data-cy="accionId" type="text" />
              <ValidatedField label="Codigo Accion" id="orden-codigoAccion" name="codigoAccion" data-cy="codigoAccion" type="text" />
              <ValidatedField label="Operacion" id="orden-operacion" name="operacion" data-cy="operacion" type="text" />
              <ValidatedField label="Precio" id="orden-precio" name="precio" data-cy="precio" type="text" />
              <ValidatedField label="Cantidad" id="orden-cantidad" name="cantidad" data-cy="cantidad" type="text" />
              <ValidatedField
                label="Fecha Operacion"
                id="orden-fechaOperacion"
                name="fechaOperacion"
                data-cy="fechaOperacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Modo" id="orden-modo" name="modo" data-cy="modo" type="text" />
              <ValidatedField
                label="Operacion Exitosa"
                id="orden-operacionExitosa"
                name="operacionExitosa"
                data-cy="operacionExitosa"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Operacion Observaciones"
                id="orden-operacionObservaciones"
                name="operacionObservaciones"
                data-cy="operacionObservaciones"
                type="text"
              />
              <ValidatedField label="Ejecutada" id="orden-ejecutada" name="ejecutada" data-cy="ejecutada" check type="checkbox" />
              <ValidatedField label="Reportada" id="orden-reportada" name="reportada" data-cy="reportada" check type="checkbox" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/orden" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Volver</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Guardar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OrdenUpdate;
