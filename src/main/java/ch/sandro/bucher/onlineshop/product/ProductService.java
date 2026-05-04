package ch.sandro.bucher.onlineshop.product;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// @Service sagt Spring Boot, dass hier unsere Geschäftslogik liegt
@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Konstruktor-Injektion: Spring Boot gibt uns automatisch das richtige Repository
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // CREATE: Ein neues Produkt speichern
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // READ (Alle): Alle Produkte aus der Datenbank holen
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // READ (Einzeln): Ein bestimmtes Produkt anhand der ID suchen
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // UPDATE: Ein bestehendes Produkt aktualisieren
    public Product updateProduct(Long id, Product productDetails) {
        // Wir suchen zuerst, ob das Produkt existiert
        return productRepository.findById(id).map(existingProduct -> {
            // Wenn ja, aktualisieren wir die Werte
            existingProduct.setName(productDetails.getName());
            existingProduct.setPreis(productDetails.getPreis());
            existingProduct.setBestand(productDetails.getBestand());
            // Und speichern es wieder
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Produkt mit ID " + id + " nicht gefunden"));
    }

    // DELETE: Ein Produkt löschen
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}