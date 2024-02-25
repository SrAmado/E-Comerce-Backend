package ecomerce.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReviewDto {

    private Long id;
    private Long rating;
    private String description;
    private MultipartFile img;
    private byte[] returnedImg;
    private Long idUser;
    private Long idProduct;
    private String username;
}
