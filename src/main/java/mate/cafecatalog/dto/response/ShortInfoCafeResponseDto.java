package mate.cafecatalog.dto.response;

import java.util.Set;
import lombok.Data;

@Data
public class ShortInfoCafeResponseDto {
    private Long id;
    private String name;
    private String logoLink;
    private Set<String> imageLink;
    private String priceLevel;
    private Set<String> optionNames;
    private String address;
    private String hours;
    private String shortDescription;
    private Integer totalPages;
    private Long totalElements;
    private Integer pageNumber;
    private Integer pageSize;
}
