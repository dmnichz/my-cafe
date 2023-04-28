package mate.cafecatalog.service.impl;

import java.util.List;
import java.util.Optional;
import mate.cafecatalog.exception.DataProcessingException;
import mate.cafecatalog.model.Cafe;
import mate.cafecatalog.model.User;
import mate.cafecatalog.repository.UserRepository;
import mate.cafecatalog.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new DataProcessingException("User with id " + id + " not found"));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void addToFavourites(Cafe cafe, User user) {
        List<Cafe> favourites;
        favourites = user.getFavourites();
        if (!favourites.contains(cafe)) {
            favourites.add(cafe);
            user.setFavourites(favourites);
            userRepository.save(user);
        }
    }

    @Override
    public void removeFromFavourites(Cafe cafe, User user) {
        List<Cafe> favourites;
        favourites = user.getFavourites();
        if (favourites.contains(cafe)) {
            favourites.remove(cafe);
            user.setFavourites(favourites);
            userRepository.save(user);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmailOrUsername(String email, String username) {
        return userRepository.findByEmailOrUsername(email, username);
    }

    public String getUserEmail() {
        return ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
    }
}
