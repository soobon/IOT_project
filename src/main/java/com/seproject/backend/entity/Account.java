//package com.seproject.backend.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(
//        name = "accounts"
//)
//public class Account implements UserDetails {
//    @Id
//    @Column(name = "username", length = 20)
//    private String username;
//    @Column(name = "passwords", length = 100)
//    private String password;
//    @Column(name = "rolee",
//            columnDefinition = "int check (rolee = 1 OR rolee = 2)"
//    )
//    private Integer role;
//
////    @Column(name = "web_role")
////    @Enumerated(EnumType.STRING)
////    private Role web_role;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
////        return List.of(new SimpleGrantedAuthority(web_role.name()));
//        return null;
//    }
//
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
////        return UserDetails.super.isAccountNonExpired();
//        return true;
//    }
//
//
//    @Override
//    public boolean isAccountNonLocked() {
////        return UserDetails.super.isAccountNonLocked();
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
////        return UserDetails.super.isCredentialsNonExpired();
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
////        return UserDetails.super.isEnabled();
//        return true;
//    }
//}
