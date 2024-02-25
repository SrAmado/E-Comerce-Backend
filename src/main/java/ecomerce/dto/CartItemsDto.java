package ecomerce.dto;

import lombok.Data;

@Data
public class CartItemsDto {

    private Long id;
    private Long price;
    private Long quantity;

    private Long idProduct;
    private  Long idOrder;
    private String productName;
    private byte[] returnedImg;

    private  Long idUser;

}

