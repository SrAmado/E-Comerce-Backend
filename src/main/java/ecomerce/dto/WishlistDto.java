package ecomerce.dto;

import lombok.Data;

@Data
public class WishlistDto {

    private Long idUser;
    private Long idProduct;
    private Long id;
    private String productName;
    private String productDescription;
    private byte[] returnedImg;
    private Long price;
}
