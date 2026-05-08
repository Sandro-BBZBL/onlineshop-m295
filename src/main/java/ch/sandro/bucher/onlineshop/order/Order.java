package ch.sandro.bucher.onlineshop.order;

import ch.sandro.bucher.onlineshop.product.Product;
import ch.sandro.bucher.onlineshop.customer.Customer; // Import der Customer-Klasse
import jakarta.persistence.*;

@Entity
@Table(name = "bestellungen")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer menge;

    //ersetzen des Strings "benutzername" durch die Customer-Beziehung
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    //Leerer Standard-Konstruktor
    public Order() {
    }

    //Konstruktor mit Feldern
    public Order(Product product, Integer menge, Customer customer) {
        this.product = product;
        this.menge = menge;
        this.customer = customer;
    }

    //Getter und Setter
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}