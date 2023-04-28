package mate.cafecatalog.dto.mapper;

import lombok.RequiredArgsConstructor;
import mate.cafecatalog.dto.request.CommentRequestDto;
import mate.cafecatalog.dto.response.CommentResponseDto;
import mate.cafecatalog.model.Comment;
import mate.cafecatalog.service.CafeService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper implements RequestDtoMapper<CommentRequestDto, Comment>,
        ResponseDtoMapper<CommentResponseDto, Comment> {
    private final CafeService cafeService;

    @Override
    public Comment mapToModel(CommentRequestDto dto) {
        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setRating(dto.getRating());
        comment.setCafe(cafeService.get(dto.getCafeId()));
        return comment;
    }

    @Override
    public CommentResponseDto mapToDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setRating(comment.getRating());
        dto.setPublicityDate(comment.getPublicityDate());
        dto.setCafeName(comment.getCafe().getName());
        dto.setUsername(comment.getUser().getUsername());
        return dto;
    }
}
