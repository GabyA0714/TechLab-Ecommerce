package com.techlab.ecommerce.repository;

import com.techlab.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByFechaBetween(LocalDate desde, LocalDate hasta);
    List<Pedido> findByClienteIgnoreCase(String cliente); // nuevo
}

