package mate.cafecatalog.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserLoginDto {
    @NotBlank(message = "Login can't be null or blank!")
    private String username;
    @NotBlank(message = "Login can't be null or blank!")
    private String password;
}
