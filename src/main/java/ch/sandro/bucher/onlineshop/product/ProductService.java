package ch.sandro.bucher.onlineshop.product;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(productDetails.getName());
            existingProduct.setPreis(productDetails.getPreis());
            existingProduct.setBestand(productDetails.getBestand());
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Produkt mit ID " + id + " nicht gefunden"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}