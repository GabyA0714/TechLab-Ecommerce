package com.techlab.ecommerce.controller;

import com.techlab.ecommerce.model.Pedido;
import com.techlab.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Crear nuevo pedido
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody Pedido pedido) {
        try {
            Pedido nuevo = pedidoService.crearPedido(pedido);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Actualizar estado del pedido
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody String nuevoEstado) {
        try {
            Optional<Pedido> pedidoOpt = pedidoService.obtenerPorId(id);
            if (pedidoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Pedido pedido = pedidoOpt.get();
            pedido.setEstado(nuevoEstado.replace("\"", ""));
            Pedido actualizado = pedidoService.guardar(pedido);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar todos los pedidos
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.obtenerTodos();
    }

    // Filtrar pedidos entre fechas
    @GetMapping("/filtrar")
    public ResponseEntity<List<Pedido>> filtrarPorFechas(
            @RequestParam("desde") String desdeStr,
            @RequestParam("hasta") String hastaStr) {
        try {
            LocalDate desde = LocalDate.parse(desdeStr);
            LocalDate hasta = LocalDate.parse(hastaStr);
            List<Pedido> pedidos = pedidoService.buscarPorFechas(desde, hasta);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Buscar pedidos por nombre de cliente
    @GetMapping("/cliente")
    public ResponseEntity<List<Pedido>> pedidosPorCliente(@RequestParam("nombre") String nombre) {
        List<Pedido> pedidos = pedidoService.buscarPorCliente(nombre);
        return ResponseEntity.ok(pedidos);
    }
}

