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
                if (datos.length == 7) {
                    ClienteDTO cliente = new ClienteDTO(
                            Integer.parseInt(datos[0]), datos[1], datos[2],
                            datos[3], datos[4], datos[5], datos[6]
                    );
                    clientes.add(cliente);
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
