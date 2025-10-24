package br.com.fintech.API.user.model;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.infra.exceptions.InvalidRequestException;
import br.com.fintech.API.user.model.enums.InvestorLevel;
import br.com.fintech.API.user.model.enums.UserRole;
import br.com.fintech.API.user.model.dto.user.UpdateUserRequest;
import br.com.fintech.API.user.model.dto.user.UserResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static br.com.fintech.API.infra.utils.SanitizerUtils.*;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "document_number", nullable = false, unique = true)
    private String document;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private InvestorLevel investorLevel;

    public void merge(UpdateUserRequest request) {
        if (request.getName() != null) this.name = request.getName();
        if (request.getDocument() != null) this.document = sanitize(request.getDocument());
        if  (request.getPhoneNumber() != null) this.phoneNumber = sanitize(request.getPhoneNumber());
        if (request.getEmail() != null) this.email = request.getEmail().trim().toLowerCase();
    }

    public UserResponse toUserResponse() {
        return UserResponse.builder()
                .name(this.name)
                .document(this.document)
                .phoneNumber(this.phoneNumber)
                .birthDate(this.birthDate)
                .email(this.email)
                .investorLevel(this.investorLevel)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.userRole == UserRole.AUTHORIZED) return List.of(new SimpleGrantedAuthority("AUTHORIZED"), new SimpleGrantedAuthority("USER"));
        else return List.of(new SimpleGrantedAuthority("USER"));
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