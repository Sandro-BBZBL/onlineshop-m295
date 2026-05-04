package ch.sandro.bucher.onlineshop;

import ch.sandro.bucher.onlineshop.order.OrderService;
import ch.sandro.bucher.onlineshop.product.Product;
import ch.sandro.bucher.onlineshop.product.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testCreateOrderInsufficientStock() {
        // Vorbereitung: Produkt mit nur 5 Stück Bestand
        Product p = new Product();
        p.setId(1L);
        p.setBestand(5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        // Test: Bestellung von 10 Stück muss eine Exception werfen
        Assertions.assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(1L, 10, "testuser");
        });
    }
}