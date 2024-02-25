package ecomerce.dto;

import lombok.Data;

@Data
public class PlaceOrderDto {

    private Long idUser;
    private String address;
    private String orderDescription;
}
