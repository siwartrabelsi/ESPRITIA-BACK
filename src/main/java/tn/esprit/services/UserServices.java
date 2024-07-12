package tn.esprit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.entities.IRole;
import tn.esprit.entities.User;
import tn.esprit.repositories.UserRepository;

import java.util.*;

@Service
public class UserServices implements IUserServices {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public Set<User> getAllUsers() {
        return new HashSet<>(userRepository.findAll());
    }

    @Override
    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User bannirUnbanUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBanned(!user.isBanned());
            return userRepository.save(user);
        } else {
            // Handle the case where the user is not found
            throw new UsernameNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
            }
        };
    }
    @Override
    public Map<String, Long> getUsersByRole() {
        List<Object[]> data = userRepository.countUsersByRole();
        Map<String, Long> result = new HashMap<>();

        for (Object[] row : data) {
            IRole role = (IRole) row[0];
            Long count = (Long) row[1];
            result.put(role.name(), count);
        }

        return result;
    }
    @Override
    public User getUserByUserName(String username){
        Optional<User> user = userRepository.findByEmail(username);
        return user.orElse(null);
    }
}
