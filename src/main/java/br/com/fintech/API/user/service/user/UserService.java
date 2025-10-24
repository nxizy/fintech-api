package br.com.fintech.API.user.service.user;

import br.com.fintech.API.user.model.User;
import br.com.fintech.API.user.model.dto.user.UpdateUserRequest;

public interface UserService {
    User update(String id, UpdateUserRequest data);

    void delete(String id);
}
