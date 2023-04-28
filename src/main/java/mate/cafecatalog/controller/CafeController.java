package mate.cafecatalog.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.cafecatalog.dto.mapper.ResponseDtoMapper;
import mate.cafecatalog.dto.mapper.ShortInfoCafeMapper;
import mate.cafecatalog.dto.response.CafeResponseDto;
import mate.cafecatalog.dto.response.ShortInfoCafeResponseDto;
import mate.cafecatalog.model.Cafe;
import mate.cafecatalog.service.CafeService;
import mate.cafecatalog.util.SortParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafe")
public class CafeController {
    private static final String FILTER_KEY_PAGE = "page";
    private static final String FILTER_KEY_COUNT = "count";
    private static final String FILTER_KEY_SORT = "sortBy";
    private final CafeService cafeService;
    private final ResponseDtoMapper<CafeResponseDto, Cafe> cafeResponseDtoMapper;
    private final ResponseDtoMapper<ShortInfoCafeResponseDto, Cafe> shortInfoCafeResponseDtoMapper;
    private final SortParser sortParser;

    @GetMapping
    public List<ShortInfoCafeResponseDto> getAll(@RequestParam(defaultValue = "0")
                                        Integer page,
                                                 @RequestParam (defaultValue = "6")
                                        Integer count,
                                                 @RequestParam (defaultValue = "id")
                                        String sortBy,
                                                 @RequestParam
                                            Map<String, String> params) {
        params.remove(FILTER_KEY_PAGE);
        params.remove(FILTER_KEY_COUNT);
        params.remove(FILTER_KEY_SORT);
        Page<Cafe> pages = cafeService.findAll(params, PageRequest.of(page, count, sortParser.parse(sortBy)));
        return pages.stream()
                .map(c -> new ShortInfoCafeMapper().mapToDto(c))
                        .peek(c -> {
                            c.setTotalPages(pages.getTotalPages());
                            c.setTotalElements(pages.getTotalElements());
                            c.setPageNumber(pages.getNumber());
                            c.setPageSize(pages.getSize());
                        })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CafeResponseDto get(@PathVariable Long id) {
        return cafeResponseDtoMapper.mapToDto(cafeService.get(id));
    }
}
