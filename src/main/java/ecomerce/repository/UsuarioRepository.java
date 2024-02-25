package ecomerce.repository;

import ecomerce.enums.UserRole;
import ecomerce.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findFirstByEmail(String email);
    Usuario findByRole(UserRole userRole);
    Optional<Usuario> findByEmail(String email);
}