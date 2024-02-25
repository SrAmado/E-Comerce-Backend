package ecomerce.controller.admin;


import ecomerce.models.Category;
import ecomerce.service.admin.category.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "Endpoind para la gestión de Categorias")
@RestController
@RequestMapping("/api/admin")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return new  ResponseEntity (categoryService.createCategory(category), HttpStatus.CREATED);

    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());

    }

    @GetMapping("/pruebas")
    public String prueba() {
        return "Hola estas autorizado";
    }
}
