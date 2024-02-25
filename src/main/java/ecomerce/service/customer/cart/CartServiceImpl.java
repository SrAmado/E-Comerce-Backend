package ecomerce.service.customer.cart;


import ecomerce.dto.AddProductInCartDto;
import ecomerce.dto.CartItemsDto;
import ecomerce.dto.OrderDto;
import ecomerce.dto.PlaceOrderDto;
import ecomerce.enums.OrderStatus;
import ecomerce.exception.ValidationException;
import ecomerce.models.*;
import ecomerce.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{

    private final OrderRepository orderRepository;
    private final UsuarioRepository userRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    public CartServiceImpl(OrderRepository orderRepository, UsuarioRepository userRepository, CartItemsRepository cartItemsRepository, ProductRepository productRepository, CouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.productRepository = productRepository;
        this.couponRepository = couponRepository;
    }

    //Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdUserId(addProductInCartDto.getIdProduct(), orderActive.getId(), addProductInCartDto.getIdUser());

    //System.out.println("orden n° "+ orderActive.getId());
    //System.out.println("id Producto "+ addProductInCartDto.getIdProduct());
    // System.out.println("id usuario "+ addProductInCartDto.getIdUser());

    @Override
    public ResponseEntity<?> addProductsToCart(AddProductInCartDto addProductInCartDto) {
        Order orderActive = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getIdUser(), OrderStatus.Pendiing);

        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProduct_IdAndOrder_IdAndUser_Id(addProductInCartDto.getIdProduct(), orderActive.getId(), addProductInCartDto.getIdUser());

        if (optionalCartItems.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("error entra aqui");
        } else {
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getIdProduct());
            Optional<Usuario> optionalUser = userRepository.findById(addProductInCartDto.getIdUser());

            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                CartItems cartItems = new CartItems();

                cartItems.setProduct(optionalProduct.get());
                cartItems.setPrice(optionalProduct.get().getPrice());
                cartItems.setQuantity(1L);
                cartItems.setUser(optionalUser.get());
                cartItems.setOrder(orderActive);

                CartItems updateCartItems = cartItemsRepository.save(cartItems);

                orderActive.setTotalAmount(orderActive.getTotalAmount() + cartItems.getPrice());
                orderActive.setAmount(orderActive.getAmount() + cartItems.getPrice());
                orderActive.getCartItems().add(cartItems);

                orderRepository.save(orderActive);

                    return ResponseEntity.status(HttpStatus.CREATED).body(cartItems.getId());

                    } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o producto no esta");
            }
        }
    }

    @Transactional(readOnly = true)
    public OrderDto getCartByUserId(Long idUser) {
        Order orderActive = orderRepository.findByUserIdAndOrderStatus(idUser, OrderStatus.Pendiing);

        List<CartItemsDto> cartItemsDtoList = orderActive.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(orderActive.getAmount());
        orderDto.setId(orderActive.getId());
        orderDto.setOrderStatus(orderActive.getOrderStatus());
        orderDto.setDiscount(orderActive.getDiscount());
        orderDto.setTotalAmount(orderActive.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);
        if (orderActive.getCoupon() != null) {
            orderDto.setCouponName(orderActive.getCoupon().getName());
        }

        return orderDto;
    }

    public OrderDto applyCoupon(Long idUser, String code) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(idUser,OrderStatus.Pendiing);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() ->new ValidationException("cupon no fount"));

        if(couponIsExpired(coupon)) {
            throw new ValidationException("Cupon expirado");
        }

        double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;

        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long) discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);

        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return expirationDate != null && currentDate.after(expirationDate);
    }

    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getIdUser(),OrderStatus.Pendiing);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getIdProduct());

        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProduct_IdAndOrder_IdAndUser_Id(
                addProductInCartDto.getIdProduct(), activeOrder.getId(), addProductInCartDto.getIdUser()
        );

        if (optionalProduct.isPresent() && optionalCartItems.isPresent()) {
            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity() + 1);

            if (activeOrder.getCoupon() != null) {
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemsRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return activeOrder.getOrderDto();
    }

    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getIdUser(),OrderStatus.Pendiing);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getIdProduct());

        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProduct_IdAndOrder_IdAndUser_Id(
                addProductInCartDto.getIdProduct(), activeOrder.getId(), addProductInCartDto.getIdUser()
        );

        if (optionalProduct.isPresent() && optionalCartItems.isPresent()) {
            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity() - 1);

            if (activeOrder.getCoupon() != null) {
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemsRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return activeOrder.getOrderDto();
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getIdUser(), OrderStatus.Pendiing);

        Optional<Usuario> optionalUser = userRepository.findById(placeOrderDto.getIdUser());

        if (optionalUser.isPresent()) {
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);

            Order order = new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pendiing);
            orderRepository.save(order);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    public List<OrderDto> getMyPlacedOrders(Long idUser) {
        return orderRepository.findByUserIdAndOrderStatusIn(idUser, List.of(OrderStatus.Placed, OrderStatus.Shipped,
                OrderStatus.Delivered)).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto searchOrderByTrackingId(UUID trackingId) {
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);

        return optionalOrder.get().getOrderDto();
    }
}
