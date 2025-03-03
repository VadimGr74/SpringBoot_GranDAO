package org.example.spring_recuperacion.repository;

import org.example.spring_recuperacion.dto.ClienteDTO;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class DAOFicherosTXT {
    private final String archivoClientes = "src/main/resources/data/clientes.txt";

    public List<ClienteDTO> leerFicheroTXT() {
        List<ClienteDTO> clientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoClientes))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                System.out.println(datos.length);
                // Validar que la línea tiene 7 valores
                if (datos.length == 7) {
                    try {
                        Integer id = Integer.parseInt(datos[0]);
                        String nombre = datos[1];
                        String apellido = datos[2];
                        String nickname = datos[3];
                        String password = datos[4];
                        String telefono = datos[5];
                        String domicilio = datos[6];
                        ClienteDTO clienteDTO = new ClienteDTO();
                        clienteDTO.setId(id);
                        clienteDTO.setNombre(nombre);
                        clienteDTO.setApellido(apellido);
                        clienteDTO.setNickname(nickname);
                        clienteDTO.setPassword(password);
                        clienteDTO.setTelefono(telefono);
                        clienteDTO.setDomicilio(domicilio);
                        clientes.add(clienteDTO);
                        System.out.println(clienteDTO.toString());
                    } catch (NumberFormatException e) {
                        System.err.println("Error al convertir ID en línea: " + linea);
                    }
                } else {
                    System.err.println("Línea incorrecta, no tiene 7 valores: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientes;
    }


    public void escribirFicheroTXT(List<ClienteDTO> clientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoClientes))) {
            for (ClienteDTO cliente : clientes) {
                bw.write(cliente.getId() + "," + cliente.getNombre() + "," + cliente.getApellido() + "," +
                        cliente.getNickname() + "," + cliente.getPassword() + "," + cliente.getTelefono() + "," + cliente.getDomicilio());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
