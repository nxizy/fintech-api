package br.com.fintech.API.user.service.auth;

import br.com.fintech.API.user.model.User;
import br.com.fintech.API.user.model.dto.user.CreateUserRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {
    User register(CreateUserRequest data);

    UserDetails findByEmail(String email);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
