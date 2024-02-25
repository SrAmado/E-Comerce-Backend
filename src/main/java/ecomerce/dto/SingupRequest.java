package ecomerce.dto;

import lombok.Data;

@Data
public class SingupRequest {

    private String email;
    private String password;
    private String name;
}
