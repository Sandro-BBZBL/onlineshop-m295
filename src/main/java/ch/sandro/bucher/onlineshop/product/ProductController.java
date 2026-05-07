package ch.sandro.bucher.onlineshop.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Endpoints für die Verwaltung von Produkten")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Alle Produkte abrufen", description = "Gibt eine Liste aller im Shop verfügbaren Produkte zurück.")
    @ApiResponse(responseCode = "200", description = "Liste erfolgreich geladen")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Produkt nach ID suchen", description = "Liefert die Details eines spezifischen Produkts.")
    @ApiResponse(responseCode = "200", description = "Produkt gefunden")
    @ApiResponse(responseCode = "404", description = "Produkt mit dieser ID existiert nicht")
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "Eindeutige ID des Produkts") @PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Neues Produkt erstellen", description = "Legt ein neues Produkt an. Zugriff nur für Admins.")
    @ApiResponse(responseCode = "201", description = "Produkt erfolgreich erstellt")
    @ApiResponse(description = "Keine Berechtigung", responseCode = "403")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Produkt aktualisieren", description = "Ändert die Daten eines bestehenden Produkts. Zugriff nur für Admins.")
    @ApiResponse(responseCode = "200", description = "Produkt erfolgreich aktualisiert")
    @ApiResponse(responseCode = "404", description = "Produkt nicht gefunden")
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "ID des zu ändernden Produkts") @PathVariable Long id,
            @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Produkt löschen", description = "Entfernt ein Produkt aus der Datenbank. Zugriff nur für Admins.")
    @ApiResponse(responseCode = "204", description = "Produkt erfolgreich gelöscht")
    @ApiResponse(responseCode = "500", description = "Löschen fehlgeschlagen (z.B. wegen bestehender Bestellungen)")
    public void deleteProduct(
            @Parameter(description = "ID des zu löschenden Produkts") @PathVariable Long id) {
        productService.deleteProduct(id);
    }
}