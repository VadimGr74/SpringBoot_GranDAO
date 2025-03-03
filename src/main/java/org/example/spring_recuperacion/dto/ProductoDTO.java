package org.example.spring_recuperacion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class ProductoDTO {
    @JsonProperty("id")
    private Integer id;

    @NotNull(message = "El nombre no puede ser nulo")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "El nombre solo puede contener letras, números y espacios")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    private Float precio;

    @NotNull(message = "El stock no puede ser nulo")
    private Integer stock;

    public ProductoDTO(Integer id, String nombre, String descripcion, Float precio, Integer stock) {
    }
    public ProductoDTO() {}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "El nombre no puede ser nulo") @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "El nombre solo puede contener letras, números y espacios") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull(message = "El nombre no puede ser nulo") @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "El nombre solo puede contener letras, números y espacios") String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public @NotNull(message = "El precio no puede ser nulo") Float getPrecio() {
        return precio;
    }

    public void setPrecio(@NotNull(message = "El precio no puede ser nulo") Float precio) {
        this.precio = precio;
    }

    public @NotNull(message = "El stock no puede ser nulo") Integer getStock() {
        return stock;
    }

    public void setStock(@NotNull(message = "El stock no puede ser nulo") Integer stock) {
        this.stock = stock;
    }
}
