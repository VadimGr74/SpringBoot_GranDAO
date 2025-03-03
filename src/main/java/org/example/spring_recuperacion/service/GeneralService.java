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

import java.util.*;
import java.util.stream.Collectors;

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
        List<ClienteDTO> clientes = daoClientes.leerFicheroTXT();

        // Evita devolver una lista con datos nulos
        if (clientes == null || clientes.isEmpty()) {
            System.err.println("⚠ No se encontraron clientes en el archivo.");
            return new ArrayList<>(); // Devuelve una lista vacía en lugar de `null`
        }

        return clientes;
    }

    public ClienteDTO obtenerClientePorId(Integer idBuscado) {
        return daoClientes.leerFicheroTXT().stream()
                .filter(clienteDTO-> clienteDTO.getId() != null && clienteDTO.getId().equals(idBuscado)) // ✅ Evita NullPointerException
                .findFirst()
                .orElse(null);
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

    public ProductoDTO obtenerProductoPorId(Integer id) {
        return daoProductos.leerFicheroXML().stream()
                .filter(productoDTO -> productoDTO.getId() != null && productoDTO.getId().equals(id))
                .findFirst()
                .orElse(null); // Return null if no product is found
    }

    public void guardarProductos(List<ProductoDTO> productos) {
        daoProductos.escribirFicheroXML(productos);
    }

    public ProductoDTO actualizarProducto(Integer id, ProductoDTO productoDTO) {
        List<ProductoDTO> productos = daoProductos.leerFicheroXML();
        for (int i = 0; i < productos.size(); i++) {
            if (Objects.equals(id, productos.get(i).getId())) {
                productos.set(i, productoDTO);
                daoProductos.escribirFicheroXML(productos);
                return productoDTO;
            }
        }
        throw new RuntimeException("Producto no encontrado con ID: " + productoDTO.getId());
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
        Producto producto = convertirProductoDTO(obtenerProductoPorId(compraGuardada.getProducto()));
        producto.setStock(producto.getStock() - compraGuardada.getCantidad());
        System.out.println("CompraDTO recibido: " + compraGuardada);
        actualizarProducto(producto.getId(),(convertirProductoDTO(producto)));
        return convertirCompraACompraDTO(compraGuardada);
    }


    public void eliminarCompra(Integer id) {
        compraRepository.deleteById(id);
    }

    // ==============================
    // CRUD para Devoluciones usando MongoDB Repository
    // ==============================

    public List<DevolucioneDTO> obtenerTodasLasDevoluciones() {
        // Traer todas las devoluciones del repositorio
        List<Devolucione> devoluciones = devolucionRepository.findAll();

        // Convertir cada devolución a su correspondiente DTO
        List<DevolucioneDTO> devolucionesDTO = devoluciones.stream()
                .filter(Objects::nonNull) // Evitar nulos
                .map(this::convertirDevolucionADevolucionDTO)
                .collect(Collectors.toList());


        return devolucionesDTO;
    }


    public Optional<DevolucioneDTO> obtenerDevolucionPorId(Integer id) {
        Devolucione devolucione = devolucionRepository.findDevolucioneById(id);
        DevolucioneDTO devolucioneDTO = convertirDevolucionADevolucionDTO(devolucione);
        return Optional.of(devolucioneDTO);
    }

    public DevolucioneDTO guardarDevolucion(DevolucioneDTO devolucionDTO) {
        Devolucione devolucion = convertirDevolucionDTOADevolucion(devolucionDTO);
        Devolucione devolucionGuardada = devolucionRepository.save(devolucion);
        Producto producto = convertirProductoDTO(obtenerProductoPorId(devolucionGuardada.getProducto().getId()));
        producto.setStock(producto.getStock() + devolucionGuardada.getCantidad());
        System.out.println("CompraDTO recibido: " + devolucionGuardada);
        actualizarProducto(producto.getId(),(convertirProductoDTO(producto)));
        return convertirDevolucionADevolucionDTO(devolucionGuardada);
    }

    public Integer eliminarDevolucion(Integer id) {

        int i = devolucionRepository.deleteDevolucioneById(id);
        if (i > 0) {
            System.out.println("Devolucion eliminada con ID: " + id);
            return i;
        } else {
            throw new RuntimeException("Devolucion no encontrado con ID: " + id);
        }
    }

    // ==============================
    // Métodos de conversión entre Entidades y DTOs
    // ==============================

    private CompraDTO convertirCompraACompraDTO(Compra compra) {
        ClienteDTO clienteDTO = obtenerClientePorId(compra.getCliente());
        Cliente cliente = convertirClienteDTO(clienteDTO);
        ProductoDTO productoDTO = obtenerProductoPorId(compra.getProducto());
        Producto producto = convertirProductoDTO(productoDTO);
        return new CompraDTO(compra.getId(), cliente, producto, compra.getFecha(), compra.getCantidad(), compra.getImporte());
    }


    public Compra convertirCompraDTOACompra(CompraDTO compraDTO) {
        Compra compra = new Compra();
        compra.setCantidad(compraDTO.getCantidad());
        compra.setFecha(compraDTO.getFecha());
        compra.setImporte(compraDTO.getImporte());

        // Validar y asignar Cliente
        if (compraDTO.getCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(compraDTO.getCliente().getId());
            compra.setCliente(cliente.getId());
        } else {
            throw new IllegalArgumentException("El cliente o el cliente_id no pueden ser nulos");
        }

        // Validar y asignar Producto
        if (compraDTO.getProducto() != null) {
            Producto producto = new Producto();
            producto.setId(compraDTO.getProducto().getId());
            compra.setProducto(producto.getId());
        } else {
            throw new IllegalArgumentException("El producto o el producto_id no pueden ser nulos");
        }

        return compra;
    }


    private DevolucioneDTO convertirDevolucionADevolucionDTO(Devolucione devolucion) {
        ClienteDTO clienteDTO = obtenerClientePorId(devolucion.getCliente().getId());
        Cliente cliente = convertirClienteDTO(clienteDTO);
        ProductoDTO productoDTO = obtenerProductoPorId(devolucion.getProducto().getId());
        Producto producto = convertirProductoDTO(productoDTO);
        return new DevolucioneDTO(devolucion.getId(), cliente, producto, devolucion.getFecha(), devolucion.getCantidad(), devolucion.getMotivo());
    }
    private Cliente convertirClienteDTO(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(), clienteDTO.getNombre(), clienteDTO.getApellido(), clienteDTO.getNickname(), clienteDTO.getPassword(), clienteDTO.getTelefono(), clienteDTO.getDomicilio());
    }
    private Producto convertirProductoDTO(ProductoDTO productoDTO) {
        return new Producto(productoDTO.getId(), productoDTO.getNombre(), productoDTO.getDescripcion(),  productoDTO.getPrecio(), productoDTO.getStock());
    }

    private ClienteDTO convertirClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setNickname(cliente.getNickname());
        clienteDTO.setPassword(cliente.getPassword());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setDomicilio(cliente.getDomicilio());
        return clienteDTO;
    }
    private ProductoDTO convertirProductoDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setStock(producto.getStock());
        return productoDTO;

    }

    private Devolucione convertirDevolucionDTOADevolucion(DevolucioneDTO devolucionDTO) {
        return new Devolucione(devolucionDTO.getId(), devolucionDTO.getCliente(), devolucionDTO.getProducto(), devolucionDTO.getFecha(), devolucionDTO.getCantidad(), devolucionDTO.getMotivo());
    }
}
