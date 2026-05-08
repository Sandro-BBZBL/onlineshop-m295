package ch.sandro.bucher.onlineshop.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Verwaltung der Kundendaten")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Alle Kunden auflisten", description = "Abrufen von Kundenliste (nur Admin)")
    @ApiResponse(responseCode = "200", description = "Kunden erfolgreich geladen")
    public List<Customer> getAll() { return service.getAll(); }

    @PostMapping
    @Operation(summary = "Kundenprofil erstellen", description = "Legt einen neuen Kunden an.")
    @ApiResponse(responseCode = "201", description = "Kunde erfolgreich angelegt")
    @ApiResponse(responseCode = "409", description = "Username bereits vergeben") // <--- Neue Message
    @ApiResponse(responseCode = "400", description = "Ungültige Daten")
    public Customer create(@RequestBody Customer customer) { return service.save(customer); }

    @GetMapping("/{username}")
    @Operation(summary = "Kunden nach Username suchen", description = "Findet einen Kunden anhand seines Username")
    @ApiResponse(responseCode = "200", description = "Kunde gefunden")
    @ApiResponse(responseCode = "404", description = "Kein Kunde mit diesem Username gefunden")
    public Customer getByUsername(@PathVariable String username) { return service.getByUsername(username); }
}