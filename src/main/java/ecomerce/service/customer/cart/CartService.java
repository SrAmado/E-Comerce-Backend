package ecomerce.service.customer.cart;


import ecomerce.dto.AddProductInCartDto;
import ecomerce.dto.OrderDto;
import ecomerce.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CartService {
    ResponseEntity<?> addProductsToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long idUser);
    OrderDto applyCoupon(Long idUser, String code);

    OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);
    OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);

    OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getMyPlacedOrders(Long idUser);

    OrderDto searchOrderByTrackingId(UUID trackingId);
}
