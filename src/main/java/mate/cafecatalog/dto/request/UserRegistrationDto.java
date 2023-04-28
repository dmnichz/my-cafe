package mate.cafecatalog.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mate.cafecatalog.validation.Email;
import mate.cafecatalog.validation.Password;

@Setter
@Getter
@ToString
@Password(field = "password", fieldMatch = "repeatedPassword")
public class UserRegistrationDto {
    @Email
    private String email;
    @NotEmpty(message = "The password couldn't be empty")
    @Size(min = 8, message = "Password must be at least 8 symbols long")
    private String password;
    private String repeatedPassword;
    @NotEmpty(message = "The username couldn't be empty.")
    private String username;
}
