package pl.swietek.springbootapi.requests.auth;


import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}