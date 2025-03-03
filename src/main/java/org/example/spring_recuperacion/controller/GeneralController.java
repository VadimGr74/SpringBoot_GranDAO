package org.example.spring_recuperacion.controller;

import org.bson.types.ObjectId;
import org.example.spring_recuperacion.dto.ClienteDTO;
import org.example.spring_recuperacion.dto.CompraDTO;
import org.example.spring_recuperacion.dto.DevolucioneDTO;
import org.example.spring_recuperacion.dto.ProductoDTO;
import org.example.spring_recuperacion.modelo.Compra;
import org.example.spring_recuperacion.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GeneralController {

    @Autowired
    private GeneralService generalService;

    // ==============================
    // CLIENTES ENDPOINTS (CRUD)
    // ==============================

    // Obtener todos los clientes
    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        return ResponseEntity.ok(generalService.obtenerTodosLosClientes());

    }

    // Obtener un cliente por ID
    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Integer id) {
        ClienteDTO clienteDTO =  generalService.obtenerClientePorId(id);
               if (clienteDTO != null) {
                   return ResponseEntity.ok(clienteDTO);
               } else {
                   return ResponseEntity.notFound().build();
               }
    }

    // Guardar una lista de clientes
    @PostMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> saveClientes(@Validated @RequestBody List<ClienteDTO> clientes) {
        for (ClienteDTO cliente : clientes) {
            System.out.println("Datos recibidos: " + cliente);
        }
        generalService.guardarClientes(clientes);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Actualizar un cliente por ID
    @PutMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Integer id, @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO actualizado = generalService.actualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un cliente por ID
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        generalService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // ==============================
    // PRODUCTOS ENDPOINTS (CRUD)
    // ==============================

    // Obtener todos los productos
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return ResponseEntity.ok(generalService.obtenerTodosLosProductos());
    }

    // Obtener un producto por ID
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Integer id) {
        ProductoDTO productoDTO = generalService.obtenerProductoPorId(id);
        if (productoDTO != null) {
            return ResponseEntity.ok(productoDTO);
        }
        return ResponseEntity.notFound().build();
    }

    // Guardar una lista de productos
    @PostMapping("/productos")
    public ResponseEntity<Void> saveProductos(@Validated @RequestBody List<ProductoDTO> productos) {
        for (ProductoDTO producto : productos) {
            System.out.println("Datos recibidos: " + producto.toString());
        }
        generalService.guardarProductos(productos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Actualizar un producto por ID
    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Integer id, @RequestBody ProductoDTO productoDTO) {
        ProductoDTO actualizado = generalService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un producto por ID
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        generalService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    // ==============================
    // COMPRAS ENDPOINTS
    // ==============================

    @GetMapping("/compras")
    public ResponseEntity<List<CompraDTO>> getAllCompras() {
        return ResponseEntity.ok(generalService.obtenerTodasLasCompras());
    }

    @GetMapping("/compras/{id}")
    public ResponseEntity<CompraDTO> getCompraById(@PathVariable Integer id) {
        return generalService.obtenerCompraPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/compras")
    public ResponseEntity<?> createCompra(@RequestBody CompraDTO compraDTO) {
        System.out.println("Datos recibidos: " + compraDTO.toString());

        if (compraDTO.getCliente() == null) {
            return ResponseEntity.badRequest().body("El cliente no puede ser nulo y debe tener un ID.");
        }
        if (compraDTO.getProducto() == null) {
            return ResponseEntity.badRequest().body("El producto no puede ser nulo y debe tener un ID.");
        }

        CompraDTO savedCompra = generalService.guardarCompra(compraDTO);
        return ResponseEntity.ok(savedCompra);
    }


    @DeleteMapping("/compras/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Integer id) {
        generalService.eliminarCompra(id);
        return ResponseEntity.noContent().build();
    }

    // ==============================
    // DEVOLUCIONES ENDPOINTS
    // ==============================

    @GetMapping("/devoluciones")
    public ResponseEntity<List<DevolucioneDTO>> getAllDevoluciones() {
        return ResponseEntity.ok(generalService.obtenerTodasLasDevoluciones());
    }

    @GetMapping("/devoluciones/{id}")
    public ResponseEntity<DevolucioneDTO> getDevolucionById(@PathVariable Integer id) {
        return generalService.obtenerDevolucionPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/devoluciones")
    public ResponseEntity<DevolucioneDTO> createDevolucion(@RequestBody DevolucioneDTO devolucioneDTO) {
        return ResponseEntity.ok(generalService.guardarDevolucion(devolucioneDTO));
    }

    @DeleteMapping("/devoluciones/{id}")
    public ResponseEntity<Void> deleteDevolucion(@PathVariable Integer id) {
        int i = generalService.eliminarDevolucion(id);
        if (i > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
