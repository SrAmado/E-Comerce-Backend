package ecomerce.service.customer.review;

import ecomerce.dto.OrderProductsResponseDto;
import ecomerce.dto.ProductDto;
import ecomerce.dto.ReviewDto;
import ecomerce.models.*;
import ecomerce.repository.OrderRepository;
import ecomerce.repository.ProductRepository;
import ecomerce.repository.ReviewRepository;
import ecomerce.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;
    private final UsuarioRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UsuarioRepository userRepository, ReviewRepository reviewRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }
    @Override
    @Transactional
    public OrderProductsResponseDto getOrderProductsDetailByOrderId(Long idOrder) {

        Optional<Order> optionalOrder = orderRepository.findById(idOrder);
        OrderProductsResponseDto orderProductsResponseDto = new OrderProductsResponseDto();

        if (optionalOrder.isPresent()) {
            orderProductsResponseDto.setOrderAmount(optionalOrder.get().getAmount());

            List<ProductDto> productDtoList = new ArrayList<>();
            for (CartItems cartItem: optionalOrder.get().getCartItems()){
                ProductDto productDto = new ProductDto();
                productDto.setId(cartItem.getProduct().getId());
                productDto.setName(cartItem.getProduct().getName());
                productDto.setPrice(cartItem.getPrice());
                productDto.setQuantity(cartItem.getQuantity());

                productDto.setByteImg(cartItem.getProduct().getImg());

                productDtoList.add(productDto);
            }
            orderProductsResponseDto.setProductDtoList(productDtoList);
        }
        return orderProductsResponseDto;
    }

    @Transactional
    public ReviewDto giveReview(ReviewDto reviewDto) {

        Optional<Product> optionalProduct = productRepository.findById(reviewDto.getIdProduct());
        Optional<Usuario> userOptional = userRepository.findById(reviewDto.getIdUser());


        if (optionalProduct.isPresent() && userOptional.isPresent()){
            System.out.println("presente");
        }else {
            System.out.println("no presente ");
        }

            try {
            if (optionalProduct.isPresent() && userOptional.isPresent()) {
                Review review = new Review();
                review.setRating(reviewDto.getRating());
                review.setDescription(reviewDto.getDescription());
                review.setUser(userOptional.get());
                review.setProduct(optionalProduct.get());
                review.setImg(reviewDto.getImg().getBytes());

               return reviewRepository.save(review).getDto();
            }
        } catch (Exception e){}

        return null;
    }
}
