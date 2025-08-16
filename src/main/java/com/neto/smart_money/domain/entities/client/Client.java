package com.neto.smart_money.domain.entities.client;

import com.neto.smart_money.domain.enums.ClientRole;
import com.neto.smart_money.dto.ClientResponseDTO;
import com.neto.smart_money.dto.LoginRequestDTO;
import com.neto.smart_money.dto.RegisterRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String email;
    private String password;

    //adding the roles
    @Enumerated(EnumType.STRING)
    private ClientRole role;

    public Client(LoginRequestDTO data) {
        this.email = data.email();
        this.password = data.password();
    }

    public Client(RegisterRequestDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
        this.role = data.role();
    }

    public Client(UUID id, ClientResponseDTO data) {
        this.id = data.id();
        this.name = data.name();
        this.email = data.email();
    }

    //methods for the login
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == ClientRole.ADMIN) return List.of(new SimpleGrantedAuthority(
                "ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_CLIENT"));
        else return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
