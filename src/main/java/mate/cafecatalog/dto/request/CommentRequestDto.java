package mate.cafecatalog.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommentRequestDto {
    @Size(max = 3000)
    private String text;
    @Min(1)
    @Max(5)
    private Integer rating;
    @Positive
    private Long cafeId;
}
