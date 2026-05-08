package ch.sandro.bucher.onlineshop.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // WICHTIGER NEUER IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "Verarbeitung und Speicherung von Bestellungen")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //Nur User machen hier Bestellungen
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('user')")
    @Operation(summary = "Bestellung aufgeben", description = "Erstellt eine neue Bestellung und speichert diese in der DB")
    @ApiResponse(responseCode = "201", description = "Bestellung erfolgreich gespeichert")
    @ApiResponse(responseCode = "400", description = "Ungültige Bestelldaten")
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

    //Nur Admins dürfen eine Liste aller Bestellungen sehen
    @GetMapping
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Alle bestellungen abrufen", description = "Liefert alle vergangenen Bestellungen")
    @ApiResponse(responseCode = "200", description = "History erfolgreich geladen")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    //User dürfen ihre eigenen sehen, Admins dürfen auch reinschauen
    @GetMapping("/user/{benutzername}")
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    @Operation(summary = "Bestellhistorie eines User abrufen", description = "Liefert alle vergangenen Bestellungen des aktuell angemeldeten Benutzers.")
    @ApiResponse(responseCode = "200", description = "Bestellungen erfolgreich geladen")
    @ApiResponse(responseCode = "401", description = "Nicht authentifiziert (Token fehlt oder ungültig)")
    @ApiResponse(responseCode = "403", description = "Keine Berechtigung auf diese Bestellung")
    @ApiResponse(responseCode = "404", description = "Benutzername nicht gefunden")
    public List<Order> getOrdersByUser(@PathVariable String benutzername) {
        return orderService.getOrdersByBenutzername(benutzername);
    }
}