package mate.cafecatalog.dto.response;

import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;
    private List<CafeResponseDto> favourites;
    private Set<RoleResponseDto> roles;
    private List<CommentResponseDto> comments;
}
