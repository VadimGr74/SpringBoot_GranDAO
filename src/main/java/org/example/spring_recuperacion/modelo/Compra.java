package org.example.spring_recuperacion.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "compras")
public class Compra {    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id", nullable = false)
private Integer id;
    @Column(name = "cliente_id",nullable = false)
    private Integer cliente;

    @Column(name = "producto_id", nullable = false)
    private Integer producto;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "importe")
    private Float importe;

    @Column(name = "cantidad")
    private Integer cantidad;
    public Compra() {

    }


    public Compra(Integer id, Integer cliente, Integer producto, @NotNull(message = "La fecha no puede ser nula") LocalDate fecha, Integer cantidad, Float importe) {
    this.id = id;
    this.cliente = cliente;
    this.producto = producto;
    this.fecha = fecha;
    this.importe = importe;
    this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}