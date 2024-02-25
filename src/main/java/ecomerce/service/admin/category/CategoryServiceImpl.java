package ecomerce.service.admin.category;


import ecomerce.models.Category;
import ecomerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl  implements CategoryService{

    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
       return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
