package org.example.spring_recuperacion.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.example.spring_recuperacion.modelo.Cliente;
import org.example.spring_recuperacion.modelo.Producto;

import java.time.LocalDate;

public class DevolucioneDTO {
    private Integer id;

    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;

    @NotNull(message = "El producto es obligatorio")
    private Producto producto;

    @NotNull(message = "La fecha no puede ser nula")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private Integer cantidad;

    private String motivo;

    public DevolucioneDTO(Integer id, Cliente cliente, Producto producto, LocalDate fecha, Integer cantidad, String motivo) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public @NotNull(message = "El cliente es obligatorio") Cliente getCliente() {
        return cliente;
    }

    public void setCliente(@NotNull(message = "El cliente es obligatorio") Cliente cliente) {
        this.cliente = cliente;
    }

    public @NotNull(message = "El producto es obligatorio") Producto getProducto() {
        return producto;
    }

    public void setProducto(@NotNull(message = "El producto es obligatorio") Producto producto) {
        this.producto = producto;
    }

    public @NotNull(message = "La fecha no puede ser nula") LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(@NotNull(message = "La fecha no puede ser nula") LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
