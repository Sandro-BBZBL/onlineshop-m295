package ch.sandro.bucher.onlineshop;

import ch.sandro.bucher.onlineshop.product.Product;
import ch.sandro.bucher.onlineshop.product.ProductRepository;
import ch.sandro.bucher.onlineshop.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductControllerTests {
    private ProductService productService;
    private final ProductRepository productRepositoryMock = mock(ProductRepository.class);
    private final Product productMock = mock(Product.class);

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepositoryMock);
    }

    @Test
    void createProduct() {
        when(productRepositoryMock.save(any())).thenReturn(productMock);
        productService.createProduct(new Product());
        verify(productRepositoryMock, times(1)).save(any());
    }

    @Test
    void deleteProduct() {
        productService.deleteProduct(1L);
        verify(productRepositoryMock, times(1)).deleteById(any());
    }
}
