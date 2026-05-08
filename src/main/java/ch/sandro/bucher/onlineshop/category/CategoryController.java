package ch.sandro.bucher.onlineshop.category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Verwaltung der Kategorien")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Alle Kategorien aufrufen", description = "Gibt eine Liste aller Kategorien aus")
    @ApiResponse(responseCode = "200", description = "Liste erfolgreich geladen")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Neue Kategorie erstellen", description = "Legt eine neue Organisationsstruktur für Produkte an. (Nur Admin)")
    @ApiResponse(responseCode = "201", description = "Kategorie erfolgreich erstellt")
    @ApiResponse(responseCode = "403", description = "Keine Admin-Rechte")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Kategorie löschen", description = "Löscht eine Kategorie, wenn kein Produkt damit verknüpft ist (Nur Admin)")
    @ApiResponse(responseCode = "204", description = "Erfolgreich gelöscht")
    @ApiResponse(responseCode = "409", description = "Löschen nicht möglich")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}