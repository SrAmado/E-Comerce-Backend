package ecomerce.security.auth.controller;

import ecomerce.dto.SingupRequest;
import ecomerce.dto.UserDto;
import ecomerce.models.Usuario;
import ecomerce.security.auth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ecomerce.security.auth.model.AccessToken;
import ecomerce.security.auth.model.Login;
import ecomerce.security.auth.jwt.TokenProvider;

import java.util.Optional;


@Tag(name = "Auth", description = "Endpoind para la gestión de obtencion de Token")
@RestController
@RequestMapping
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    private final AuthService authService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, AuthService authService){
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;

        this.authService = authService;
    }

    @PostMapping("/authentication")
    ResponseEntity<AccessToken> obtenerToken (@RequestBody Login login){

        UsernamePasswordAuthenticationToken username = new UsernamePasswordAuthenticationToken(
                login.getUsername(),
                login.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<Usuario> optionalUser = authService.findByEmailLogin(login.getUsername());
        String userId = String.valueOf(optionalUser.get().getId());
        String role = String.valueOf(optionalUser.get().getRole());
        String token = tokenProvider.crearToken(authentication);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(new AccessToken(token, userId,role));
    }

    @PostMapping("/sing-up")
    public ResponseEntity<?> singupUser(@RequestBody SingupRequest singupRequest) {
        if (authService.hasUserWithEmail(singupRequest.getEmail())) {
            return new ResponseEntity<>("Usuario existe ", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(singupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
}