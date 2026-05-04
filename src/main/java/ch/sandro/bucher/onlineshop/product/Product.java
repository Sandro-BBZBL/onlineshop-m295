package ch.sandro.bucher.onlineshop.product;

import ch.sandro.bucher.onlineshop.category.Category; // Wichtig: Den Import deiner neuen Entity hinzufügen
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

    // Die Verknüpfung zur Kategorie
    @ManyToOne
    @JoinColumn(name = "category_id") // Erzeugt eine Fremdschlüssel-Spalte in der Tabelle 'produkte'
    private Category category;

    // --- Leerer Standard-Konstruktor ---
    public Product() {
    }

    // --- Konstruktor mit Feldern (Kategorie optional hinzugefügt) ---
    public Product(String name, Double preis, Integer bestand, Category category) {
        this.name = name;
        this.preis = preis;
        this.bestand = bestand;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}