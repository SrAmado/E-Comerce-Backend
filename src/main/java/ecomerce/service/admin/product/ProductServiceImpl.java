package ecomerce.service.admin.product;

import ecomerce.dto.ProductDto;
import ecomerce.models.Category;
import ecomerce.models.Product;
import ecomerce.repository.CategoryRepository;
import ecomerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl  implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg().getBytes());

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();

        product.setCategory(category);
        return productRepository.save(product).getDto();
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products= productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Transactional
    public List<ProductDto> getAllProductByName(String name) {
        List<Product> products= productRepository.findAllByNameContainingIgnoreCase(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();

        if (product != null) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    public ProductDto getProductById(Long idProduct) {
        Optional<Product> optionalProduct = productRepository.findById(idProduct);
        return optionalProduct.get().getDto();
    }

    public ProductDto updateProduct(Long idProduct, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(idProduct);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        Product product = optionalProduct.get();

        try {

            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategory(optionalCategory.get());

            if (productDto.getImg() != null) {
                product.setImg(productDto.getImg().getBytes());
            }
            productRepository.save(product);

        } catch (Exception e) {}

        return product.getDto();
    }
}
