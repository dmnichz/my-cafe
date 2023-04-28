package mate.cafecatalog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mate.cafecatalog.security.jwt.JwtTokenProvider;
import mate.cafecatalog.security.token.Token;
import mate.cafecatalog.security.token.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String token = jwtTokenProvider.resolveToken(request);
        Token storedToken = tokenService.findByToken(token).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenService.save(storedToken);
        }
    }
}
