package ecomerce.controller.admin;

import ecomerce.models.Coupon;
import ecomerce.service.coupon.CouponService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Coupon", description = "Endpoind para la gestión de Cupones")
@RestController
@RequestMapping("/api/admin/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
        return ResponseEntity.ok(couponService.createCoupon(coupon));
    }
    @GetMapping
    public ResponseEntity<?> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }
}
