package com.example.api.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@Entity
@RequiredArgsConstructor

@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id" )
  private   int id;
    @Column(name = "username")
   private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "numberphone")
    private String numberphone;

    @Column(name = "address")
    private String address;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name ="users_roles",
            joinColumns =@JoinColumn(name = "user_id"),
        inverseJoinColumns =@JoinColumn(name = "role_id")
    )
    private Set<Role> roles= new HashSet<>();
    @Column(name = "verify_phone")
    private boolean verify_phone;
    @JsonIgnore
    @OneToOne(mappedBy = "idUser",cascade = CascadeType.ALL)
    private Cart cart;
    @JsonIgnore
    @OneToMany(mappedBy = "userId",cascade = CascadeType.ALL)
    private List<Order> order;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority>authorities =roles.stream()
                .map(role->new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", numberphone='" + numberphone + '\'' +
                ", address='" + address + '\'' +
                ", roles=" + roles +
                ", verify_phone=" + verify_phone +
                '}';
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
