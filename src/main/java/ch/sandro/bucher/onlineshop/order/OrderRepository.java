package ch.sandro.bucher.onlineshop.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Sucht in der Order-Entity im Feld 'customer' nach dem 'username'
    List<Order> findByCustomer_Username(String username);
}