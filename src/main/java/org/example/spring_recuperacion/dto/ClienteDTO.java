package org.example.spring_recuperacion.dto;


import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class ClienteDTO {
    private Integer id;

    //@Pattern(regexp = "^[a-zA-Z]+$", message = "El nombre solo puede contener letras")
    @NotNull(message = "El nombre no puede ser nulo")
    private String nombre;

    @NotNull(message = "El apellido no puede ser nulo")
    private String apellido;

    @NotNull(message = "El nickname no puede ser nulo")
    private String nickname;

    @Size(min = 6, max = 10, message = "La contraseña debe tener entre 6 y 10 caracteres")
   // @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "La contraseña solo puede contener letras y números")
    @NotNull(message = "La contraseña no puede ser nula")
    private String password;


    //@Pattern(regexp = "^\\+34 [679][0-9]{2} [0-9]{3} [0-9]{3}$", message = "Telefono en formato incorrecto")
    private String telefono;

    private String domicilio;

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", domicilio='" + domicilio + '\'' +
                '}';
    }

    public ClienteDTO(Integer id, String nombre, String apellido, String nickname, String password, String telefono, String domicilio) {
    }
    public ClienteDTO() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @Pattern(regexp = "^[a-zA-Z]+$", message = "El nombre solo puede contener letras") @NotNull(message = "El nombre no puede ser nulo") String getNombre() {
        return nombre;
    }

    public void setNombre(@Pattern(regexp = "^[a-zA-Z]+$", message = "El nombre solo puede contener letras") @NotNull(message = "El nombre no puede ser nulo") String nombre) {
        this.nombre = nombre;
    }

    public @NotNull(message = "El apellido no puede ser nulo") String getApellido() {
        return apellido;
    }

    public void setApellido(@NotNull(message = "El apellido no puede ser nulo") String apellido) {
        this.apellido = apellido;
    }

    public @NotNull(message = "El nickname no puede ser nulo") String getNickname() {
        return nickname;
    }

    public void setNickname(@NotNull(message = "El nickname no puede ser nulo") String nickname) {
        this.nickname = nickname;
    }

    public @Size(min = 6, max = 10, message = "La contraseña debe tener entre 6 y 10 caracteres") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "La contraseña solo puede contener letras y números") @NotNull(message = "La contraseña no puede ser nula") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 6, max = 10, message = "La contraseña debe tener entre 6 y 10 caracteres") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "La contraseña solo puede contener letras y números") @NotNull(message = "La contraseña no puede ser nula") String password) {
        this.password = password;
    }

    public @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos") @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo puede contener números") String getTelefono() {
        return telefono;
    }

    public void setTelefono(@Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos") @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo puede contener números") String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
}
