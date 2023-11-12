package ar.edu.um.programacion2.procesador.service.dto;

import ar.edu.um.programacion2.procesador.domain.Orden;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
@AllArgsConstructor
@NoArgsConstructor*/
public class OrdenesDTO {

    public List<Orden> ordenes;

    public OrdenesDTO() {}

    public OrdenesDTO(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    public List<Orden> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }
}
