import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrden } from 'app/shared/model/orden.model';
import { getEntities } from './orden.reducer';

export const Orden = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const ordenList = useAppSelector(state => state.orden.entities);
  const loading = useAppSelector(state => state.orden.loading);
  const totalItems = useAppSelector(state => state.orden.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="orden-heading" data-cy="OrdenHeading">
        Ordens
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/orden/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Orden
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ordenList && ordenList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clienteId')}>
                  Cliente Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('accionId')}>
                  Accion Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('codigoAccion')}>
                  Codigo Accion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('operacion')}>
                  Operacion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('precio')}>
                  Precio <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cantidad')}>
                  Cantidad <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaOperacion')}>
                  Fecha Operacion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('modo')}>
                  Modo <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('operacionExitosa')}>
                  Operacion Exitosa <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('operacionObservaciones')}>
                  Operacion Observaciones <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ejecutada')}>
                  Ejecutada <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reportada')}>
                  Reportada <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ordenList.map((orden, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/orden/${orden.id}`} color="link" size="sm">
                      {orden.id}
                    </Button>
                  </td>
                  <td>{orden.clienteId}</td>
                  <td>{orden.accionId}</td>
                  <td>{orden.codigoAccion}</td>
                  <td>{orden.operacion}</td>
                  <td>{orden.precio}</td>
                  <td>{orden.cantidad}</td>
                  <td>{orden.fechaOperacion ? <TextFormat type="date" value={orden.fechaOperacion} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{orden.modo}</td>
                  <td>{orden.operacionExitosa ? 'true' : 'false'}</td>
                  <td>{orden.operacionObservaciones}</td>
                  <td>{orden.ejecutada ? 'true' : 'false'}</td>
                  <td>{orden.reportada ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/orden/${orden.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/orden/${orden.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/orden/${orden.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningún Ordens encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={ordenList && ordenList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Orden;
