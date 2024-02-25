package ecomerce.controller.customer;

import ecomerce.dto.WishlistDto;
import ecomerce.service.customer.wishlist.WishlistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Wishlist", description = "Endpoind para la gestión de mis favoritos")
@RestController
@RequestMapping("/api/customer")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlist/{idUser}")
    public ResponseEntity<?> getWishlistByUserId(@PathVariable Long idUser){
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(idUser));
    }

    @PostMapping("/wishlist")
    public ResponseEntity<?> addProductToWishlist(@RequestBody WishlistDto wishlistDto) {
        return new ResponseEntity(wishlistService.addProductToWishlist(wishlistDto), HttpStatus.CREATED);
    }
}
