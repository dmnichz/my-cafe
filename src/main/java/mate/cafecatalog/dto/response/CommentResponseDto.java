package mate.cafecatalog.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommentResponseDto {
    private Long id;
    private String text;
    private Integer rating;
    private LocalDateTime publicityDate;
    private String cafeName;
    private String username;
}
