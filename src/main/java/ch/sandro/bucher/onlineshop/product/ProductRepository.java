package ch.sandro.bucher.onlineshop.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository sagt Spring Boot, dass dies unsere Datenbank-Schnittstelle ist
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Da wir JpaRepository erweitern (extends), bekommen wir automatisch
    // Methoden wie save(), findAll(), findById() und deleteById() geschenkt!
}