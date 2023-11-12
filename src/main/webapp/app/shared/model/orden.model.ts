import dayjs from 'dayjs';

export interface IOrden {
  id?: number;
  clienteId?: number | null;
  accionId?: number | null;
  codigoAccion?: string | null;
  operacion?: string | null;
  precio?: number | null;
  cantidad?: number | null;
  fechaOperacion?: string | null;
  modo?: string | null;
  operacionExitosa?: boolean | null;
  operacionObservaciones?: string | null;
  ejecutada?: boolean | null;
  reportada?: boolean | null;
}

export const defaultValue: Readonly<IOrden> = {
  operacionExitosa: false,
  ejecutada: false,
  reportada: false,
};
