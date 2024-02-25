package ecomerce.service.admin.category;

import ecomerce.models.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(Category category);
    List<Category> getAllCategories();
}
