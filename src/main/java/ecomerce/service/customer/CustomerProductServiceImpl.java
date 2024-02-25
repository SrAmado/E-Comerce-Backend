package ecomerce.service.customer;

import ecomerce.dto.ProductDetailDto;
import ecomerce.dto.ProductDto;
import ecomerce.models.FAQ;
import ecomerce.models.Product;
import ecomerce.models.Review;
import ecomerce.repository.FAQRepository;
import ecomerce.repository.ProductRepository;
import ecomerce.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductRepository productRepository;
    private final FAQRepository faqRepository;
    private final ReviewRepository reviewRepository;

    public CustomerProductServiceImpl(ProductRepository productRepository, FAQRepository faqRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.faqRepository = faqRepository;
        this.reviewRepository = reviewRepository;
    }
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products= productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Transactional
    public List<ProductDto> getAllProductByName(String name) {
        List<Product> products= productRepository.findAllByNameContainingIgnoreCase(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public ProductDetailDto getProductDetailsById(Long idProduct) {
        Optional<Product> optionalProduct = productRepository.findById(idProduct);
        ProductDetailDto productDetailDto = new ProductDetailDto();

        if (optionalProduct.isPresent()) {
            List<FAQ> faqList = faqRepository.findAllByProductId(idProduct);
            List<Review> reviewList = reviewRepository.findAllByProductId(idProduct);

            productDetailDto.setProductDto(optionalProduct.get().getDto());
            productDetailDto.setFaqDtoList(faqList.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
            productDetailDto.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));
        }

        return productDetailDto;
    }
}
