package ecomerce.controller.customer;


import ecomerce.dto.ProductDto;
import ecomerce.service.customer.CustomerProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Customer", description = "Endpoind para la gestión del Cliente")
@RestController
@RequestMapping("/api/customer")
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    public CustomerProductController(CustomerProductService customerProductService) {
        this.customerProductService = customerProductService;
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(customerProductService.getAllProducts());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProducts(@PathVariable String name) {
        return ResponseEntity.ok(customerProductService.getAllProductByName(name));
    }
    @GetMapping("/product/{idProduct}")
    public ResponseEntity<?> getProductDetailsById(@PathVariable Long idProduct) {
        return ResponseEntity.ok(customerProductService.getProductDetailsById(idProduct));
    }
}
