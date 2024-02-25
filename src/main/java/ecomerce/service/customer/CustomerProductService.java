package ecomerce.service.customer;

import ecomerce.dto.ProductDetailDto;
import ecomerce.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductByName(String name);

    ProductDetailDto getProductDetailsById(Long idProduct);
}
