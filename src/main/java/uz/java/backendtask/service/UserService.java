package uz.java.backendtask.service;

import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.UserCriteria;
import uz.java.backendtask.dto.UserResponseDTO;
import uz.java.backendtask.entity.User;
import uz.java.backendtask.security.SecurityUser;
import uz.java.backendtask.specification.PageRequest;

public interface UserService {

    User findByUsername(String username);

    UserResponseDTO getById(Long id);

    UserResponseDTO getMe(SecurityUser user);

    Page<UserResponseDTO> search(PageRequest request, UserCriteria criteria);

    User findById(Long id);
}
