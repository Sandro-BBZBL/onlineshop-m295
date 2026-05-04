package ch.sandro.bucher.onlineshop.product;

import jakarta.persistence.*;

@Entity
@Table(name = "produkte")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double preis;

    @Column(nullable = false)
    private Integer bestand;

    // --- Leerer Standard-Konstruktor (Wichtig für Spring Boot) ---
    public Product() {
    }

    // --- Konstruktor mit allen Feldern ---
    public Product(String name, Double preis, Integer bestand) {
        this.name = name;
        this.preis = preis;
        this.bestand = bestand;
    }

    // --- Getter und Setter ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    public Integer getBestand() {
        return bestand;
    }

    public void setBestand(Integer bestand) {
        this.bestand = bestand;
    }
}