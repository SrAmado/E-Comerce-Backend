package ecomerce.models;

import ecomerce.dto.FAQDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "faq", schema = "ecomerce")
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String question;
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product:id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public FAQDto getFAQDto() {
        FAQDto faqDto = new FAQDto();

        faqDto.setId(id);
        faqDto.setQuestion(question);
        faqDto.setAnswer(question);
        faqDto.setIdProduct(product.getId());

        return faqDto;
    }
}
