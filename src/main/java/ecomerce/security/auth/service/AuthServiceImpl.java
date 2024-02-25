package ecomerce.security.auth.service;

import ecomerce.dto.SingupRequest;
import ecomerce.dto.UserDto;
import ecomerce.enums.OrderStatus;
import ecomerce.enums.UserRole;
import ecomerce.models.Order;

import ecomerce.models.Usuario;
import ecomerce.repository.OrderRepository;
import ecomerce.repository.UsuarioRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UsuarioRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final OrderRepository orderRepository;
    public AuthServiceImpl(UsuarioRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.orderRepository = orderRepository;
    }
    @Override
    public UserDto createUser(SingupRequest singupRequest) {
        Usuario user = new Usuario();

        user.setEmail(singupRequest.getEmail());
        user.setName(singupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(singupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);

        Usuario createUser = userRepository.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createUser);
        order.setOrderStatus(OrderStatus.Pendiing);
        orderRepository.save(order);

        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());

        return userDto;
    }
    @Override
    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    @PostConstruct
    public void createAcountAmdin() {
        Usuario adminAcount = userRepository.findByRole(UserRole.ADMIN);

        if (null == adminAcount) {
            Usuario user = new Usuario();
            user.setEmail("admin@gmail.com");
            user.setName("Aministrador");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }

    @Override
    public Optional<Usuario> findByEmailLogin(String email) {
        return userRepository.findByEmail(email);
    }

}
