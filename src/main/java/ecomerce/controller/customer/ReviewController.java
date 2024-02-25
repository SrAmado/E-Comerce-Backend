package ecomerce.controller.customer;

import ecomerce.dto.ReviewDto;
import ecomerce.service.customer.review.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "Endpoind para la gestión de revisar la orden")
@RestController
@RequestMapping("/api/customer")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/order-products/{idOrder}")
    public ResponseEntity<?> getOrderProductsDetailByOrderId(@PathVariable Long idOrder) {
        return ResponseEntity.ok(reviewService.getOrderProductsDetailByOrderId(idOrder));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.giveReview(reviewDto));
    }
}
