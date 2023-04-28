package mate.cafecatalog.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import mate.cafecatalog.exception.InvalidJwtAuthenticationException;
import mate.cafecatalog.security.token.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.expire-length}")
    private long jwtExpiration;
    @Value("${security.jwt.refresh-token.expire-length}")
    private long refreshExpiration;

    private final UserDetailsService userDetailsService;

    private final TokenService tokenService;

    public JwtTokenProvider(UserDetailsService userDetailsService,
                            TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login, List<String> roles, boolean isRefreshToken) {
        if (isRefreshToken) {
            return buildToken(login, roles, refreshExpiration);
        }
        return buildToken(login, roles, jwtExpiration);
    }

    private String buildToken(String login, List<String> roles, long expiration) {
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null; // throw new RuntimeException("Can't resolve token");
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            boolean isValidToken = tokenService.findByToken(token)
                    .map(t -> ! t.isExpired() && ! t.isRevoked())
                    .orElse(false);

            return ! claims.getBody().getExpiration().before(new Date()) &&
                    isValidToken;
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtAuthenticationException("Expired JWT token", e);
        }
        catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Invalid JWT token", e);
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return ! claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtAuthenticationException("Expired JWT refresh token", e);
        }
        catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Invalid JWT refresh token", e);
        }
    }
}
