package pl.swietek.springbootapi.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.swietek.springbootapi.models.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MASTER_READ,
                    MASTER_UPDATE,
                    MASTER_DELETE,
                    MASTER_CREATE
            )
    ),
    MASTER(
            Set.of(
                    MASTER_READ,
                    MASTER_UPDATE,
                    MASTER_DELETE,
                    MASTER_CREATE
            )
    );

    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return (List<SimpleGrantedAuthority>) authorities;
    }

}

