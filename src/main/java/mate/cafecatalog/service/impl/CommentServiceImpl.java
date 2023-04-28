package mate.cafecatalog.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.cafecatalog.exception.DataProcessingException;
import mate.cafecatalog.model.Comment;
import mate.cafecatalog.model.User;
import mate.cafecatalog.repository.CommentRepository;
import mate.cafecatalog.service.CommentService;
import mate.cafecatalog.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public Comment add(Comment comment) {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String email = details.getUsername();
        User user = userService.findByEmail(email).orElseThrow(
                () -> new DataProcessingException("User with email " + email + " not found"));
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByCafeId(Long cafeId) {
        return commentRepository.findAllByCafeId(cafeId);
    }

    @Override
    public List<Comment> findAllByUserId(Long userId) {
        return commentRepository.findAllByUserId(userId);
    }

    @Override
    public Integer getAvgRatingByCafeId(Long cafeId) {
        return commentRepository.getAvgRatingByCafeId(cafeId);
    }
}
