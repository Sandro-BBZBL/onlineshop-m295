package ch.sandro.bucher.onlineshop.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Spring Boot ist schlau: Allein durch den Namen dieser Methode weiss es,
    // dass es alle Bestellungen für einen bestimmten Benutzernamen in der DB suchen soll!
    List<Order> findByBenutzername(String benutzername);
}