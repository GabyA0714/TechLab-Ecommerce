package com.techlab.ecommerce.service;

import com.techlab.ecommerce.model.Pedido;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.repository.PedidoRepository;
import com.techlab.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public Pedido crearPedido(Pedido pedido) throws Exception {
        Optional<Producto> productoOpt = productoRepository.findById(pedido.getProductoId());
        if (productoOpt.isEmpty()) throw new Exception("Producto no encontrado.");

        Producto producto = productoOpt.get();

        if (producto.getStock() < pedido.getCantidad()) {
            throw new Exception("Stock insuficiente.");
        }

        // Actualiza stock
        producto.setStock(producto.getStock() - pedido.getCantidad());
        productoRepository.save(producto);

        // Completa datos del pedido
        pedido.setTotal(producto.getPrecio() * pedido.getCantidad());
        pedido.setFecha(LocalDate.now());
        pedido.setEstado("pendiente");

        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> buscarPorFechas(LocalDate desde, LocalDate hasta) {
        return pedidoRepository.findByFechaBetween(desde, hasta);
    }

    public List<Pedido> buscarPorCliente(String cliente) {
        return pedidoRepository.findByClienteIgnoreCase(cliente);
    }
}
