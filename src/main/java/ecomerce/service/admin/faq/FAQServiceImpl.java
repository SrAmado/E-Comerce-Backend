package ecomerce.service.admin.faq;

import ecomerce.dto.FAQDto;
import ecomerce.models.FAQ;
import ecomerce.models.Product;
import ecomerce.repository.FAQRepository;
import ecomerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FAQServiceImpl implements FAQService{

    private  final FAQRepository faqRepository;
    private final ProductRepository productRepository;

    public FAQServiceImpl(FAQRepository faqRepository, ProductRepository productRepository) {
        this.faqRepository = faqRepository;
        this.productRepository = productRepository;
    }

    public FAQDto postFAQ(Long idProduct, FAQDto faqDto) {
        Optional<Product> optionalProduct = productRepository.findById(idProduct);

        if (optionalProduct.isPresent()) {
            FAQ faq = new FAQ();

            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());

            return faqRepository.save(faq).getFAQDto();
        }
        return null;
    }
}
