package org.example.spring_recuperacion.repository;

import org.example.spring_recuperacion.modelo.Cliente;
import org.example.spring_recuperacion.modelo.Compra;
import org.example.spring_recuperacion.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    public Compra findByClienteAndProducto(Cliente cliente, Producto producto);
}
