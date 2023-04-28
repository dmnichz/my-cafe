package mate.cafecatalog.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.cafecatalog.dto.response.UserLoginResponseInfo;
import mate.cafecatalog.dto.response.UserResponseDto;
import mate.cafecatalog.model.Cafe;
import mate.cafecatalog.model.User;
import mate.cafecatalog.service.CommentService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements ResponseDtoMapper<UserResponseDto, User>{

    private final CommentService commentService;
    private final CafeMapper cafeMapper;
    private final RoleMapper roleMapper;
    private final CommentMapper commentMapper;

    @Override
    public UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setFavourites(user.getFavourites()
                .stream()
                .map(cafeMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setRoles(user.getRoles()
                .stream()
                .map(roleMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setComments(commentService.findAllByUserId(user.getId())
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public UserLoginResponseInfo mapToLoginResponseInfo(User user, String token, String refreshToken) {
        UserLoginResponseInfo responseBody = new UserLoginResponseInfo();
        responseBody.setAccessToken(token);
        responseBody.setRefreshToken(refreshToken);
        responseBody.setEmail(user.getEmail());
        responseBody.setUsername(user.getUsername());
        List<Long> favouritesId = user.getFavourites().
                stream().map(Cafe::getId)
                .collect(Collectors.toList());
        responseBody.setFavouritesId(favouritesId);
        return responseBody;
    }
}
