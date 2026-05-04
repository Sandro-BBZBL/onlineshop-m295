package ch.sandro.bucher.onlineshop.order;

import ch.sandro.bucher.onlineshop.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "bestellungen") // WICHTIG: In SQL ist "order" ein reserviertes Wort, deshalb nennen wir die Tabelle "bestellungen"
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Eine Bestellung gehört zu einem Produkt. @ManyToOne bedeutet:
    // Viele Bestellungen können dasselbe Produkt beinhalten.
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer menge;

    @Column(nullable = false)
    private String benutzername; // Hier speichern wir später den Keycloak-Usernamen ab

    // --- Leerer Standard-Konstruktor ---
    public Order() {
    }

    // --- Konstruktor mit Feldern ---
    public Order(Product product, Integer menge, String benutzername) {
        this.product = product;
        this.menge = menge;
        this.benutzername = benutzername;
    }

    // --- Getter und Setter ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getMenge() {
        return menge;
    }

    public void setMenge(Integer menge) {
        this.menge = menge;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }
}