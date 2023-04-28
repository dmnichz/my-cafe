package mate.cafecatalog.dto.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.cafecatalog.dto.request.CafeRequestDto;
import mate.cafecatalog.dto.response.CafeResponseDto;
import mate.cafecatalog.model.Cafe;
import mate.cafecatalog.model.Option;
import mate.cafecatalog.model.PicturePath;
import mate.cafecatalog.service.CommentService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CafeMapper implements RequestDtoMapper<CafeRequestDto, Cafe>,
        ResponseDtoMapper<CafeResponseDto, Cafe> {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Override
    public Cafe mapToModel(CafeRequestDto dto) {
        return null;
    }

    @Override
    public CafeResponseDto mapToDto(Cafe cafe) {
        CafeResponseDto dto = new CafeResponseDto();
        dto.setId(cafe.getId());
        dto.setName(cafe.getName());
        dto.setShortDescription(cafe.getShortDescription());
        dto.setDescription(cafe.getDescription());
        dto.setCity(cafe.getCity());
        dto.setAddress(cafe.getAddress());
        dto.setHours(cafe.getHours());
        dto.setPriceLevel(cafe.getPriceLevel());
        dto.setMinOrder(cafe.getMinOrder());
        dto.setNoiseLevel(cafe.getNoiseLevel());
        dto.setTablesNumber(cafe.getTablesNumber());
        dto.setLatitude(cafe.getLatitude());
        dto.setLongitude(cafe.getLongitude());
        dto.setRating(cafe.getRating());
        dto.setInstagramLink(cafe.getInstagramLink());
        dto.setFacebookLink(cafe.getFacebookLink());
        dto.setWebsiteLink(cafe.getWebsiteLink());
        dto.setLogoLink(cafe.getLogoLink());
        dto.setOptionNames(cafe.getOptions()
                .stream()
                .map(Option::getName)
                .collect(Collectors.toSet()));
        dto.setImageLink(cafe.getImageLink()
                .stream()
                .map(PicturePath::getPath)
                .collect(Collectors.toSet()));
        dto.setComments(commentService.findAllByCafeId(cafe.getId())
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
