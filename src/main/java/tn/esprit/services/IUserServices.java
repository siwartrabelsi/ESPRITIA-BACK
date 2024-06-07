package tn.esprit.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import tn.esprit.entities.User;

import java.util.Set;

public interface IUserServices {
    User addUser(User user);
    User getUserById(Long id);
    Set<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);

    UserDetailsService userDetailsService();
}
