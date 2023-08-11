package pl.swietek.springbootapi.requests.auth;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
