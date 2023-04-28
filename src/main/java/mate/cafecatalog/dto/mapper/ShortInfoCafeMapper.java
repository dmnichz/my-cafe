package mate.cafecatalog.dto.mapper;

import java.util.stream.Collectors;
import mate.cafecatalog.dto.response.ShortInfoCafeResponseDto;
import mate.cafecatalog.model.Cafe;
import mate.cafecatalog.model.Option;
import mate.cafecatalog.model.PicturePath;
import org.springframework.stereotype.Component;

@Component
public class ShortInfoCafeMapper implements RequestDtoMapper<ShortInfoCafeResponseDto, Cafe>,
        ResponseDtoMapper<ShortInfoCafeResponseDto, Cafe> {

    @Override
    public Cafe mapToModel(ShortInfoCafeResponseDto dto) {
        return null;
    }

    @Override
    public ShortInfoCafeResponseDto mapToDto(Cafe cafe) {
        ShortInfoCafeResponseDto dto = new ShortInfoCafeResponseDto();
        dto.setId(cafe.getId());
        dto.setName(cafe.getName());
        dto.setLogoLink(cafe.getLogoLink());
        dto.setImageLink(cafe.getImageLink()
                .stream()
                .map(PicturePath::getPath)
                .collect(Collectors.toSet()));
        dto.setPriceLevel(cafe.getPriceLevel());
        dto.setOptionNames(cafe.getOptions()
                .stream()
                .map(Option::getName)
                .collect(Collectors.toSet()));
        dto.setAddress(cafe.getAddress());
        dto.setHours(cafe.getHours());
        dto.setShortDescription(cafe.getShortDescription());
        return dto;
    }
}
