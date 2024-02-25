package ecomerce.service.customer.review;

import ecomerce.dto.OrderProductsResponseDto;
import ecomerce.dto.ReviewDto;

public interface ReviewService {

    OrderProductsResponseDto getOrderProductsDetailByOrderId(Long idOrder);

    ReviewDto giveReview(ReviewDto reviewDto);
}
