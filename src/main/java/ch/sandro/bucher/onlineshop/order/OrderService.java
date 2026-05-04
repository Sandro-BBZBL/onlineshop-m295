package ch.sandro.bucher.onlineshop.order;

import ch.sandro.bucher.onlineshop.product.Product;
import ch.sandro.bucher.onlineshop.product.ProductRepository;
import ch.sandro.bucher.onlineshop.customer.Customer;
import ch.sandro.bucher.onlineshop.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository; // Hinzugefügt

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Order createOrder(Long productId, Integer menge, String benutzername) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produkt nicht gefunden."));

        // NEU: Wir suchen den echten Customer anhand des Namens
        Customer customer = customerRepository.findByUsername(benutzername)
                .orElseThrow(() -> new RuntimeException("Kunde " + benutzername + " nicht gefunden."));

        if (product.getBestand() < menge) {
            throw new RuntimeException("Nicht genügend Bestand!");
        }

        product.setBestand(product.getBestand() - menge);
        productRepository.save(product);

        // Jetzt übergeben wir das customer-OBJEKT, nicht den String
        Order order = new Order(product, menge, customer);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByBenutzername(String benutzername) {
        return orderRepository.findByCustomer_Username(benutzername);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}