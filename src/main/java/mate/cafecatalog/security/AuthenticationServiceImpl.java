package mate.cafecatalog.security;

import java.util.Optional;
import java.util.Set;
import mate.cafecatalog.exception.AuthenticationException;
import mate.cafecatalog.exception.DataProcessingException;
import mate.cafecatalog.model.User;
import mate.cafecatalog.service.RoleService;
import mate.cafecatalog.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserService userService,
                                     RoleService roleService,
                                     PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String email, String password, String username) {
        if (userService.findByEmail(email).isPresent()) {
            throw new DataProcessingException("Email " + email + " already used.");
        }
        if (userService.findByUsername(username).isPresent()) {
            throw new DataProcessingException("Username " + username + " already used.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        user.setRoles(Set.of(roleService.getRoleByName("USER")));
        user = userService.save(user);
        return user;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmailOrUsername(login, login);
        if (user.isEmpty() || ! passwordEncoder.matches(password, user.get().getPassword())) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }

        return user.get();
    }
}
