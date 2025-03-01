package org.example.spring_recuperacion.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Pattern(regexp = "^[a-zA-Z]+$")
    @NotNull
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @NotNull
    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Size(min=6, max=10)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(min=9,max=9)
    @Pattern(regexp = "^[a-zA-Z]+$")
    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "domicilio", length = 100)
    private String domicilio;

    public Cliente(Integer id, @Pattern(regexp = "^[a-zA-Z]+$", message = "El nombre solo puede contener letras") @NotNull(message = "El nombre no puede ser nulo") String nombre, @NotNull(message = "El apellido no puede ser nulo") String apellido, @NotNull(message = "El nickname no puede ser nulo") String nickname, @Size(min = 6, max = 10, message = "La contraseña debe tener entre 6 y 10 caracteres") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "La contraseña solo puede contener letras y números") @NotNull(message = "La contraseña no puede ser nula") String password, @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos") @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo puede contener números") String telefono, String domicilio) {
    }

    public Cliente() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

}