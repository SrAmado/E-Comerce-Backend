package ecomerce.service.coupon;

import ecomerce.models.Coupon;

import java.util.List;

public interface CouponService {
    Coupon createCoupon(Coupon coupon);
    List<Coupon> getAllCoupons();
}
