package com.techlab.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlab.ecommerce.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
