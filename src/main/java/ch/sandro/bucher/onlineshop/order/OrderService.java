package ch.sandro.bucher.onlineshop.order;

import ch.sandro.bucher.onlineshop.product.Product;
import ch.sandro.bucher.onlineshop.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // Konstruktor-Injektion: Wir holen uns Zugriff auf Bestellungen UND Produkte
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // Eine Bestellung aufgeben
    @Transactional
    public Order createOrder(Long productId, Integer menge, String benutzername) {
        // 1. Produkt in der Datenbank suchen
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produkt mit ID " + productId + " nicht gefunden."));

        // 2. Prüfen, ob noch genug Bestand da ist
        if (product.getBestand() < menge) {
            throw new RuntimeException("Nicht genügend Bestand! Verfügbar: " + product.getBestand());
        }

        // 3. Bestand reduzieren und Produkt aktualisieren
        product.setBestand(product.getBestand() - menge);
        productRepository.save(product);

        // 4. Die eigentliche Bestellung erstellen und speichern
        Order order = new Order(product, menge, benutzername);
        return orderRepository.save(order);
    }

    // Alle Bestellungen eines bestimmten Benutzers finden
    public List<Order> getOrdersByBenutzername(String benutzername) {
        return orderRepository.findByBenutzername(benutzername);
    }

    // Alle Bestellungen anzeigen (später nützlich für den Admin)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}