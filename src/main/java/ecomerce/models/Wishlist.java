package ecomerce.models;

import ecomerce.dto.WishlistDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "wishlist", schema = "ecomerce")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario user;

    public WishlistDto getWislistDto() {
        WishlistDto wishlistDto = new WishlistDto();

        wishlistDto.setId(id);
        wishlistDto.setIdProduct(product.getId());
        wishlistDto.setReturnedImg(product.getImg());
        wishlistDto.setProductName(product.getName());
        wishlistDto.setProductDescription(product.getDescription());
        wishlistDto.setPrice(product.getPrice());
        wishlistDto.setIdUser(user.getId());

        return wishlistDto;
    }
}
