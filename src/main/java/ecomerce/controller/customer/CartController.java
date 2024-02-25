package ecomerce.controller.customer;


import ecomerce.dto.AddProductInCartDto;
import ecomerce.dto.OrderDto;
import ecomerce.dto.PlaceOrderDto;
import ecomerce.exception.ValidationException;
import ecomerce.service.customer.cart.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart", description = "Endpoind para la gestión de Carrito Compras")
@RestController
@RequestMapping("/api/customer")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto) {
        //return ResponseEntity.ok(cartService.addProductsToCart(addProductInCartDto));
        return cartService.addProductsToCart(addProductInCartDto);
    }

    @GetMapping("/cart/{idUser}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long idUser) {
        OrderDto orderDto = cartService.getCartByUserId(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/coupon/{idUser}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long idUser, @PathVariable String code) {
        try {
            OrderDto orderDto = cartService.applyCoupon(idUser,code);
            return ResponseEntity.ok(orderDto);
        }catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/addition")
    public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto) {
        return ResponseEntity.ok(cartService.increaseProductQuantity(addProductInCartDto));
    }

    @PostMapping("/deduction")
    public ResponseEntity<OrderDto> dereseaProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto) {
        return ResponseEntity.ok(cartService.decreaseProductQuantity(addProductInCartDto));
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        return ResponseEntity.ok(cartService.placeOrder(placeOrderDto));
    }

    @GetMapping("/my-orders/{idUser}")
    public ResponseEntity<?> getMyPlacedOrders(@PathVariable Long idUser) {
        return ResponseEntity.ok(cartService.getMyPlacedOrders(idUser));
    }
}
