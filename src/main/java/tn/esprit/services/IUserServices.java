package tn.esprit.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import tn.esprit.entities.User;

import java.util.Map;
import java.util.Set;

public interface IUserServices {
    User addUser(User user);
    User getUserById(Long id);
    Set<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User bannirUnbanUser(Long id);
    UserDetailsService userDetailsService();
    public Map<String, Long> getUsersByRole();
    public User getUserByUserName(String username);
}
