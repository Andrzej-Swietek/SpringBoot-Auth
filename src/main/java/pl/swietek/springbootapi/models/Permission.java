package pl.swietek.springbootapi.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MASTER_READ("master:read"),
    MASTER_UPDATE("master:update"),
    MASTER_CREATE("master:create"),
    MASTER_DELETE("master:delete");

    private final String permission;
}
