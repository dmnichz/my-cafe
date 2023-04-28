package mate.cafecatalog.dto.response;

import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class CafeResponseDto {
    private Long id;
    private String name;
    private String shortDescription;
    private String description;
    private String city;
    private String address;
    private String hours;
    private String priceLevel;
    private Integer minOrder;
    private String noiseLevel;
    private Integer tablesNumber;
    private Double latitude;
    private Double longitude;
    private Integer rating;
    private String instagramLink;
    private String facebookLink;
    private String websiteLink;
    private String logoLink;
    private Set<String> optionNames;
    private Set<String> imageLink;
    private List<CommentResponseDto> comments;
}
