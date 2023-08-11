package pl.swietek.springbootapi.requests.user;

import lombok.Data;

@Data
public class AddUserRoleRequest {
    private String username;
    private String roleName;
}
