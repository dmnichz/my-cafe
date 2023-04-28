package mate.cafecatalog.security.token;

import java.util.List;
import java.util.Optional;

public interface TokenService {

    Token save(Token token);

    List<Token> findAllValidTokensByUser(Long id);

    Optional<Token> findByToken(String token);

    void saveAll(List<Token> validTokens);
}
