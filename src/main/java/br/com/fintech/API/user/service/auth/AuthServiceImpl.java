package br.com.fintech.API.user.service.auth;

import br.com.fintech.API.infra.exceptions.ConflictException;
import br.com.fintech.API.infra.exceptions.EmailAlreadyExistsException;
import br.com.fintech.API.user.model.User;
import br.com.fintech.API.user.model.dto.user.CreateUserRequest;
import br.com.fintech.API.user.model.enums.InvestorLevel;
import br.com.fintech.API.user.model.enums.UserRole;
import br.com.fintech.API.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public User register(CreateUserRequest data) {
        if(userRepository.findByEmail(data.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException();
        }
        if(userRepository.existsByDocument(data.getDocument())) throw new ConflictException("Document already registered");
        if(userRepository.existsByPhoneNumber(data.getPhoneNumber())) throw new ConflictException("Phone number already registered");

        if (Arrays.stream(InvestorLevel.values())
                .noneMatch(level -> level.name().equalsIgnoreCase(String.valueOf(data.getInvestorLevel())))) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid investor level");
        }

        String encryptedPassword = passwordEncoder.encode(data.getPassword());
        User user = User.builder()
                .name(data.getName())
                .document(data.getDocument())
                .phoneNumber(data.getPhoneNumber())
                .birthDate(data.getBirthDate())
                .email(data.getEmail().trim().toLowerCase())
                .password(encryptedPassword)
                .userRole(UserRole.AUTHORIZED)
                .investorLevel(data.getInvestorLevel())
                .build();

        return userRepository.save(user);
    }

    @Override
    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
