package br.com.fintech.API.user.service.user;

import br.com.fintech.API.infra.exceptions.ConflictException;
import br.com.fintech.API.infra.exceptions.InvalidRequestException;
import br.com.fintech.API.infra.exceptions.NotFoundException;
import br.com.fintech.API.user.model.User;
import br.com.fintech.API.user.model.dto.user.UpdateUserRequest;
import br.com.fintech.API.user.model.enums.InvestorLevel;
import br.com.fintech.API.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static br.com.fintech.API.infra.utils.SanitizerUtils.isCpfValid;
import static br.com.fintech.API.infra.utils.SanitizerUtils.sanitize;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder bCryptPasswordEncoder;


    @Override
    public User update(String id, UpdateUserRequest data) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if(userRepository.existsByEmail(data.getEmail())) throw new ConflictException("Email already exists");
        if(userRepository.existsByDocument(data.getDocument())) throw new ConflictException("Document already registered");
        if(userRepository.existsByPhoneNumber(data.getPhoneNumber())) throw new ConflictException("Phone number already registered");

        if (Arrays.stream(InvestorLevel.values())
                .noneMatch(level -> level.name().equalsIgnoreCase(String.valueOf(data.getInvestorLevel())))) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid investor level");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(data.getPassword());
        user.merge(data);
        user.setPassword(encodedPassword);
        String doc = sanitize(user.getDocument());
        if (doc.length() < 11 || !isCpfValid(doc)) {
            throw new InvalidRequestException("CPF invalid");
        }

        return userRepository.save(user);
    }

    @Override
    public void delete(String id) {
        if(!userRepository.existsById(id)) throw new NotFoundException("User not found");

        userRepository.deleteById(id);
    }
}
