package mate.cafecatalog.security;

import mate.cafecatalog.exception.AuthenticationException;
import mate.cafecatalog.model.User;

public interface AuthenticationService {
    User register(String email, String password, String username);

    User login(String login, String password) throws AuthenticationException;
}
