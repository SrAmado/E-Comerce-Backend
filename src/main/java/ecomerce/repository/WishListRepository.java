package ecomerce.repository;

import ecomerce.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByUserId(Long idUser);
}
