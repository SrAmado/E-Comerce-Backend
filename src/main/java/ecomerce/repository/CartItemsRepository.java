package ecomerce.repository;


import ecomerce.models.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    Optional<CartItems> findByProduct_IdAndOrder_IdAndUser_Id(Long productId, Long orderId, Long userId);

}
