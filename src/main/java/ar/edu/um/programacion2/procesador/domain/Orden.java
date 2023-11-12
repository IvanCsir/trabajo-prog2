package ar.edu.um.programacion2.procesador.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Orden.
 */
@Entity
@Table(name = "orden")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonProperty("cliente")
    @Column(name = "cliente_id")
    private Integer clienteId;

    @Column(name = "accion_id")
    private Integer accionId;

    @JsonProperty("accion")
    @Column(name = "codigo_accion")
    private String codigoAccion;

    @Column(name = "operacion")
    private String operacion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fecha_operacion")
    private Instant fechaOperacion;

    @Column(name = "modo")
    private String modo;

    @Column(name = "operacion_exitosa")
    private Boolean operacionExitosa;

    @Column(name = "operacion_observaciones")
    private String operacionObservaciones;

    @Column(name = "ejecutada")
    private Boolean ejecutada;

    @Column(name = "reportada")
    private Boolean reportada;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Orden id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return this.clienteId;
    }

    public Orden clienteId(Integer clienteId) {
        this.setClienteId(clienteId);
        return this;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getAccionId() {
        return this.accionId;
    }

    public Orden accionId(Integer accionId) {
        this.setAccionId(accionId);
        return this;
    }

    public void setAccionId(Integer accionId) {
        this.accionId = accionId;
    }

    public String getCodigoAccion() {
        return this.codigoAccion;
    }

    public Orden codigoAccion(String codigoAccion) {
        this.setCodigoAccion(codigoAccion);
        return this;
    }

    public void setCodigoAccion(String codigoAccion) {
        this.codigoAccion = codigoAccion;
    }

    public String getOperacion() {
        return this.operacion;
    }

    public Orden operacion(String operacion) {
        this.setOperacion(operacion);
        return this;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public Orden precio(Double precio) {
        this.setPrecio(precio);
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public Orden cantidad(Integer cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Instant getFechaOperacion() {
        return this.fechaOperacion;
    }

    public Orden fechaOperacion(Instant fechaOperacion) {
        this.setFechaOperacion(fechaOperacion);
        return this;
    }

    public void setFechaOperacion(Instant fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getModo() {
        return this.modo;
    }

    public Orden modo(String modo) {
        this.setModo(modo);
        return this;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public Boolean getOperacionExitosa() {
        return this.operacionExitosa;
    }

    public Orden operacionExitosa(Boolean operacionExitosa) {
        this.setOperacionExitosa(operacionExitosa);
        return this;
    }

    public void setOperacionExitosa(Boolean operacionExitosa) {
        this.operacionExitosa = operacionExitosa;
    }

    public String getOperacionObservaciones() {
        return this.operacionObservaciones;
    }

    public Orden operacionObservaciones(String operacionObservaciones) {
        this.setOperacionObservaciones(operacionObservaciones);
        return this;
    }

    public void setOperacionObservaciones(String operacionObservaciones) {
        this.operacionObservaciones = operacionObservaciones;
    }

    public Boolean getEjecutada() {
        return this.ejecutada;
    }

    public Orden ejecutada(Boolean ejecutada) {
        this.setEjecutada(ejecutada);
        return this;
    }

    public void setEjecutada(Boolean ejecutada) {
        this.ejecutada = ejecutada;
    }

    public Boolean getReportada() {
        return this.reportada;
    }

    public Orden reportada(Boolean reportada) {
        this.setReportada(reportada);
        return this;
    }

    public void setReportada(Boolean reportada) {
        this.reportada = reportada;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orden)) {
            return false;
        }
        return id != null && id.equals(((Orden) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Orden{" +
            "id=" + getId() +
            ", clienteId=" + getClienteId() +
            ", accionId=" + getAccionId() +
            ", codigoAccion='" + getCodigoAccion() + "'" +
            ", operacion='" + getOperacion() + "'" +
            ", precio=" + getPrecio() +
            ", cantidad=" + getCantidad() +
            ", fechaOperacion='" + getFechaOperacion() + "'" +
            ", modo='" + getModo() + "'" +
            ", operacionExitosa='" + getOperacionExitosa() + "'" +
            ", operacionObservaciones='" + getOperacionObservaciones() + "'" +
            ", ejecutada='" + getEjecutada() + "'" +
            ", reportada='" + getReportada() + "'" +
            "}";
    }
}
