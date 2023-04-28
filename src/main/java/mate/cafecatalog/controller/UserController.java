package mate.cafecatalog.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.cafecatalog.dto.mapper.CommentMapper;
import mate.cafecatalog.dto.mapper.ShortInfoCafeMapper;
import mate.cafecatalog.dto.request.CommentRequestDto;
import mate.cafecatalog.dto.response.CommentResponseDto;
import mate.cafecatalog.dto.response.ShortInfoCafeResponseDto;
import mate.cafecatalog.exception.DataProcessingException;
import mate.cafecatalog.model.Cafe;
import mate.cafecatalog.model.Comment;
import mate.cafecatalog.model.User;
import mate.cafecatalog.service.CafeService;
import mate.cafecatalog.service.CommentService;
import mate.cafecatalog.service.UserService;
import mate.cafecatalog.util.SortParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cabinet")
public class UserController {
    private final UserService userService;
    private final CafeService cafeService;
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final SortParser sortParser;

    @GetMapping
    public List<ShortInfoCafeResponseDto> getUserFavourites(@RequestParam(defaultValue = "0")
                                                                Integer page,
                                                            @RequestParam (defaultValue = "6")
                                                                Integer count,
                                                            @RequestParam (defaultValue = "id")
                                                                String sortBy) {
        String email = userService.getUserEmail();
        User user = userService.findByEmail(email).orElseThrow(
                () -> new DataProcessingException("User with email " + email + " not found"));
        List<Long> favourites = user.getFavourites()
                .stream()
                .map(Cafe::getId)
                .collect(Collectors.toList());
        Page<Cafe> pages = cafeService.findAllByIdIn(favourites, PageRequest.of(page, count, sortParser.parse(sortBy)));
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

    @PostMapping("/favourite")
    public void addToFavourites(@RequestParam Long cafeId) {
        String email = userService.getUserEmail();
        User user = userService.findByEmail(email).orElseThrow(
                () -> new DataProcessingException("User with email " + email + " not found"));
        Cafe cafe = cafeService.get(cafeId);
        userService.addToFavourites(cafe, user);
    }

    @PostMapping("/favourite/remove")
    public void removeFromFavourites(@RequestParam Long cafeId) {
        String email = userService.getUserEmail();
        User user = userService.findByEmail(email).orElseThrow(
                () -> new DataProcessingException("User with email " + email + " not found"));
        Cafe cafe = cafeService.get(cafeId);
        userService.removeFromFavourites(cafe, user);
    }

    @PostMapping("/comment")
    public CommentResponseDto addComment(@RequestBody @Valid CommentRequestDto requestDto) {
        Comment comment = commentService.add(commentMapper.mapToModel(requestDto));
        cafeService.processRating(comment.getCafe().getId(), comment.getRating());
        return commentMapper.mapToDto(comment);
    }
}
