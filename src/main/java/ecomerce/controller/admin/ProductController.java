package ecomerce.controller.admin;

import ecomerce.dto.FAQDto;
import ecomerce.dto.ProductDto;
import ecomerce.service.admin.faq.FAQService;
import ecomerce.service.admin.product.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "Product", description = "Endpoind para la gestión de Productos")
@RestController
@RequestMapping("/api/admin")
public class ProductController {

    private final ProductService productService;

    private final FAQService faqService;
    public ProductController(ProductService productService, FAQService faqService) {
        this.productService = productService;
        this.faqService = faqService;
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct8(@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto productDto1 = productService.addProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }
    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProducts(@PathVariable String name) {
        return ResponseEntity.ok(productService.getAllProductByName(name));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/faq/{idProduct}")
    public ResponseEntity<?> postFAQ(@PathVariable Long idProduct, @RequestBody FAQDto faqDto) {
        return ResponseEntity.ok(faqService.postFAQ(idProduct,faqDto));
    }
    @GetMapping("/product/{idProduct}")
    public ResponseEntity<?> getProductById(@PathVariable Long idProduct) {
        return ResponseEntity.ok(productService.getProductById(idProduct));
    }
    @PutMapping("/product/{idProduct}")
    public ResponseEntity<?> updateProduct(@PathVariable Long idProduct, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(idProduct,productDto));
    }
}
