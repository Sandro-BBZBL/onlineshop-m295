package ch.sandro.bucher.onlineshop.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // WICHTIGER NEUER IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // TÜRSTEHER: Nur normale User machen hier Bestellungen
    @PostMapping
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest request) {
        try {
            Order order = orderService.createOrder(
                    request.getProductId(),
                    request.getMenge(),
                    request.getBenutzername()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TÜRSTEHER: Nur Admins dürfen eine Liste ALLER Bestellungen sehen
    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // TÜRSTEHER: User dürfen ihre eigenen sehen, Admins dürfen auch reinschauen
    @GetMapping("/user/{benutzername}")
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public List<Order> getOrdersByUser(@PathVariable String benutzername) {
        return orderService.getOrdersByBenutzername(benutzername);
    }
}