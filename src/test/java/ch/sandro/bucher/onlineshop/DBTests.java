package ch.sandro.bucher.onlineshop;

import ch.sandro.bucher.onlineshop.customer.Customer;
import ch.sandro.bucher.onlineshop.customer.CustomerRepository;
import ch.sandro.bucher.onlineshop.product.Product;
import ch.sandro.bucher.onlineshop.product.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class DBTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void insertData() {
        // Produkt Test
        Product monitor = new Product("Gaming Monitor", 299.0, 5, null);
        Product savedMonitor = this.productRepository.save(monitor);
        Assertions.assertNotNull(savedMonitor.getId());

        // Customer Test
        Customer customer = new Customer();
        customer.setUsername("junit-user");
        customer.setCity("Bern");
        // Falls dein Customer Pflichtfelder hat (E-Mail etc.), musst du sie hier setzen!

        Customer savedCustomer = this.customerRepository.save(customer);
        Assertions.assertNotNull(savedCustomer.getId());
    }
}