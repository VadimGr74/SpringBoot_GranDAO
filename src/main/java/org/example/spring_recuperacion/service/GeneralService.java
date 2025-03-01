package org.example.spring_recuperacion.service;

import org.example.spring_recuperacion.dto.ClienteDTO;
import org.example.spring_recuperacion.dto.CompraDTO;
import org.example.spring_recuperacion.dto.DevolucioneDTO;
import org.example.spring_recuperacion.dto.ProductoDTO;
import org.example.spring_recuperacion.modelo.Cliente;
import org.example.spring_recuperacion.modelo.Compra;
import org.example.spring_recuperacion.modelo.Devolucione;
import org.example.spring_recuperacion.modelo.Producto;
import org.example.spring_recuperacion.repository.CompraRepository;
import org.example.spring_recuperacion.repository.DAOFicherosTXT;
import org.example.spring_recuperacion.repository.DAOFicherosXML;
import org.example.spring_recuperacion.repository.DevolucioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GeneralService {

    private final DAOFicherosTXT daoClientes;
    private final DAOFicherosXML daoProductos;
    private final CompraRepository compraRepository;
    private final DevolucioneRepository devolucionRepository;

    @Autowired
    public GeneralService(DAOFicherosTXT daoClientes, DAOFicherosXML daoProductos,
                          CompraRepository compraRepository, DevolucioneRepository devolucionRepository) {
        this.daoClientes = daoClientes;
        this.daoProductos = daoProductos;
        this.compraRepository = compraRepository;
        this.devolucionRepository = devolucionRepository;
    }

    // ==============================
    // CRUD para Clientes usando DAOFicherosTXT
    // ==============================

    public List<ClienteDTO> obtenerTodosLosClientes() {
        return daoClientes.leerFicheroTXT();
    }

    public Optional<ClienteDTO> obtenerClientePorId(Integer id) {
        return daoClientes.leerFicheroTXT().stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst();
    }

    public void guardarClientes(List<ClienteDTO> clientes) {
        daoClientes.escribirFicheroTXT(clientes);
    }

    public ClienteDTO actualizarCliente(Integer id, ClienteDTO clienteDTO) {
        List<ClienteDTO> clientes = daoClientes.leerFicheroTXT();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId().equals(id)) {
                clientes.set(i, clienteDTO);
                daoClientes.escribirFicheroTXT(clientes);
                return clienteDTO;
            }
        }
        throw new RuntimeException("Cliente no encontrado con ID: " + id);
    }

    public void eliminarCliente(Integer id) {
        List<ClienteDTO> clientes = daoClientes.leerFicheroTXT()
                .stream()
                .filter(cliente -> !cliente.getId().equals(id))
                .collect(Collectors.toList());
        daoClientes.escribirFicheroTXT(clientes);
    }

    // ==============================
    // CRUD para Productos usando DAOFicherosXML
    // ==============================

    public List<ProductoDTO> obtenerTodosLosProductos() {
        return daoProductos.leerFicheroXML();
    }

    public Optional<ProductoDTO> obtenerProductoPorId(Integer id) {
        return daoProductos.leerFicheroXML().stream()
                .filter(producto -> producto.getId().equals(id))
                .findFirst();
    }

    public void guardarProductos(List<ProductoDTO> productos) {
        daoProductos.escribirFicheroXML(productos);
    }

    public ProductoDTO actualizarProducto(Integer id, ProductoDTO productoDTO) {
        List<ProductoDTO> productos = daoProductos.leerFicheroXML();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId().equals(id)) {
                productos.set(i, productoDTO);
                daoProductos.escribirFicheroXML(productos);
                return productoDTO;
            }
        }
        throw new RuntimeException("Producto no encontrado con ID: " + id);
    }

    public void eliminarProducto(Integer id) {
        List<ProductoDTO> productos = daoProductos.leerFicheroXML()
                .stream()
                .filter(producto -> !producto.getId().equals(id))
                .collect(Collectors.toList());
        daoProductos.escribirFicheroXML(productos);
    }

    // ==============================
    // CRUD para Compras usando JPA Repository
    // ==============================

    public List<CompraDTO> obtenerTodasLasCompras() {
        List<Compra> compras = compraRepository.findAll();
        return compras.stream().map(this::convertirCompraACompraDTO).collect(Collectors.toList());
    }

    public Optional<CompraDTO> obtenerCompraPorId(Integer id) {
        return compraRepository.findById(id).map(this::convertirCompraACompraDTO);
    }

    public CompraDTO guardarCompra(CompraDTO compraDTO) {
        Compra compra = convertirCompraDTOACompra(compraDTO);
        Compra compraGuardada = compraRepository.save(compra);
        return convertirCompraACompraDTO(compraGuardada);
    }

    public void eliminarCompra(Integer id) {
        compraRepository.deleteById(id);
    }

    // ==============================
    // CRUD para Devoluciones usando MongoDB Repository
    // ==============================

    public List<DevolucioneDTO> obtenerTodasLasDevoluciones() {
        List<Devolucione> devoluciones = devolucionRepository.findAll();
        return devoluciones.stream().map(this::convertirDevolucionADevolucionDTO).collect(Collectors.toList());
    }

    public Optional<DevolucioneDTO> obtenerDevolucionPorId(String id) {
        return devolucionRepository.findById(id).map(this::convertirDevolucionADevolucionDTO);
    }

    public DevolucioneDTO guardarDevolucion(DevolucioneDTO devolucionDTO) {
        Devolucione devolucion = convertirDevolucionDTOADevolucion(devolucionDTO);
        Devolucione devolucionGuardada = devolucionRepository.save(devolucion);
        return convertirDevolucionADevolucionDTO(devolucionGuardada);
    }

    public void eliminarDevolucion(String id) {
        devolucionRepository.deleteById(id);
    }

    // ==============================
    // Métodos de conversión entre Entidades y DTOs
    // ==============================

    private CompraDTO convertirCompraACompraDTO(Compra compra) {
        return new CompraDTO(compra.getId(), compra.getCliente(), compra.getProducto(), compra.getFecha(), compra.getCantidad(), compra.getImporte());
    }

    private Compra convertirCompraDTOACompra(CompraDTO compraDTO) {
        return new Compra(compraDTO.getId(), compraDTO.getCliente(), compraDTO.getProducto(), compraDTO.getFecha(), compraDTO.getCantidad(), compraDTO.getImporte());
    }

    private DevolucioneDTO convertirDevolucionADevolucionDTO(Devolucione devolucion) {
        return new DevolucioneDTO(devolucion.getId(), devolucion.getCliente(), devolucion.getProducto(), devolucion.getFecha(), devolucion.getCantidad(), devolucion.getMotivo());
    }

    private Devolucione convertirDevolucionDTOADevolucion(DevolucioneDTO devolucionDTO) {
        return new Devolucione(devolucionDTO.getId(), devolucionDTO.getCliente(), devolucionDTO.getProducto(), devolucionDTO.getFecha(), devolucionDTO.getCantidad(), devolucionDTO.getMotivo());
    }
}
