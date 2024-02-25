package ecomerce.service.admin.faq;

import ecomerce.dto.FAQDto;


public interface FAQService {

    FAQDto postFAQ(Long idProduct, FAQDto faqDto);
}
