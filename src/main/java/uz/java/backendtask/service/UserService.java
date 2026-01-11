package uz.java.backendtask.service;

import uz.java.backendtask.entity.User;

public interface UserService {

    User findByUsername(String username);

}
