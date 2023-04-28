package mate.cafecatalog.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserLoginResponseInfo {
    private String email;
    private String username;
    @JsonProperty("token")
    private String accessToken;
    @JsonProperty("refreshToken")
    private String refreshToken;
    private List<Long> favouritesId;
}
