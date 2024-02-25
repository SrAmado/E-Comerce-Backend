package ecomerce.service.admin.product;

import ecomerce.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto) throws IOException;
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductByName(String name);
    boolean deleteProduct(Long id);

    ProductDto getProductById(Long idProduct);
    ProductDto updateProduct(Long idProduct, ProductDto productDto);
}
