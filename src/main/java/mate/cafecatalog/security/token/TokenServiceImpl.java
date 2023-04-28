package mate.cafecatalog.security.token;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public List<Token> findAllValidTokensByUser(Long id) {
        return tokenRepository.findAllValidTokensByUserId(id);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void saveAll(List<Token> validTokens) {
        tokenRepository.saveAll(validTokens);
    }
}
