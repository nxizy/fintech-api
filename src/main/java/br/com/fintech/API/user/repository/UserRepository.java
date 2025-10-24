package br.com.fintech.API.user.repository;

import br.com.fintech.API.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByDocument(String document);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}
