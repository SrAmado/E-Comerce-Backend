package ecomerce.service.customer.wishlist;

import ecomerce.dto.WishlistDto;
import ecomerce.models.Product;
import ecomerce.models.Usuario;
import ecomerce.models.Wishlist;
import ecomerce.repository.ProductRepository;
import ecomerce.repository.UsuarioRepository;
import ecomerce.repository.WishListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService{

    private final UsuarioRepository userRepository;
    private final ProductRepository productRepository;
    private final WishListRepository wishListRepository;

    public WishlistServiceImpl(UsuarioRepository userRepository, ProductRepository productRepository, WishListRepository wishListRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.wishListRepository = wishListRepository;
    }
    @Override
    public WishlistDto addProductToWishlist(WishlistDto wishlistDto) {
        Optional<Product> optionalProduct = productRepository.findById(wishlistDto.getIdProduct());
        Optional<Usuario> optionalUser = userRepository.findById(wishlistDto.getIdUser());
        Wishlist wishlist = new Wishlist();

        if (optionalProduct.isPresent() && optionalUser.isPresent()) {
            wishlist.setProduct(optionalProduct.get());
            wishlist.setUser(optionalUser.get());

            wishListRepository.save(wishlist).getWislistDto();
        }

        return wishlist.getWislistDto();
    }

    @Transactional
    @Override
    public List<WishlistDto> getWishlistByUserId(Long idUser) {
        return wishListRepository.findAllByUserId(idUser).stream().map(Wishlist::getWislistDto).collect(Collectors.toList());
    }
}
