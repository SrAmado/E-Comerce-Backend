package ecomerce.security.auth.service;

import ecomerce.dto.SingupRequest;
import ecomerce.dto.UserDto;
import ecomerce.models.Usuario;

import java.util.Optional;

public interface AuthService {

    UserDto createUser(SingupRequest singupRequest);
    Boolean hasUserWithEmail(String email);

    void createAcountAmdin();

    Optional<Usuario> findByEmailLogin(String email);
}
