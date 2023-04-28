package mate.cafecatalog.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.cafecatalog.dto.mapper.UserMapper;
import mate.cafecatalog.dto.request.UserLoginDto;
import mate.cafecatalog.dto.request.UserRegistrationDto;
import mate.cafecatalog.dto.response.UserLoginResponseInfo;
import mate.cafecatalog.dto.response.UserResponseDto;
import mate.cafecatalog.exception.AuthenticationException;
import mate.cafecatalog.model.Role;
import mate.cafecatalog.model.User;
import mate.cafecatalog.security.AuthenticationService;
import mate.cafecatalog.security.jwt.JwtTokenProvider;
import mate.cafecatalog.security.token.Token;
import mate.cafecatalog.security.token.TokenService;
import mate.cafecatalog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        User user = authenticationService.register(userRegistrationDto.getEmail(),
                userRegistrationDto.getPassword(), userRegistrationDto.getUsername());
        return userMapper.mapToDto(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto)
            throws AuthenticationException {
        User user = authenticationService.login(userLoginDto.getUsername(),
                userLoginDto.getPassword());

        revokeAllUserTokens(user);
        String accessToken = getToken(user, false);
        String refreshToken = getToken(user, true);
        UserLoginResponseInfo responseBody =
                userMapper.mapToLoginResponseInfo(user, accessToken, refreshToken);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getUsername(refreshToken);
        User user = userService.findByEmail(email).orElseThrow();
        UserLoginResponseInfo responseBody = null;
        // this validation can be removed, because it validates at JwtFilter
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            revokeAllUserTokens(user);
            String accessToken = getToken(user, false);
            responseBody =
                    userMapper.mapToLoginResponseInfo(user, accessToken, refreshToken);
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    private String getToken(User user, boolean isRefreshToken) {
        String token = jwtTokenProvider.createToken(user.getEmail(),
                user.getRoles().stream()
                        .map(Role::getRoleName)
                        .map(Enum::name)
                        .collect(Collectors.toList()), isRefreshToken);
        if (! isRefreshToken) {
            Token modelToken = Token.builder()
                    .user(user)
                    .token(token)
                    .expired(false)
                    .revoked(false)
                    .build();
            tokenService.save(modelToken);
        }
        return token;
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validTokens = tokenService.findAllValidTokensByUser(user.getId());
        if (! validTokens.isEmpty()) {
            validTokens.forEach(token -> {
                token.setRevoked(true);
                token.setExpired(true);
            });
            tokenService.saveAll(validTokens);
        }
    }
}
