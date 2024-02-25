package ecomerce.controller;


import ecomerce.service.customer.cart.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Tracking", description = "Endpoind para la gestión de Seguimientos")
@RestController
@RequestMapping("/api/customer")
public class TrackingController {


    private final CartService cartService;

    public TrackingController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/order/{idTracking}")
    public ResponseEntity<?> searchOrderByTrackingId (@PathVariable UUID idTracking) {
        return ResponseEntity.ok(cartService.searchOrderByTrackingId(idTracking));
    }
}
