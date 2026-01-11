package uz.java.backendtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.backendtask.entity.User;
import uz.java.backendtask.exception.UserException;
import uz.java.backendtask.repository.UserRepository;
import uz.java.backendtask.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new UserException("user not found: " + username));
    }
}
