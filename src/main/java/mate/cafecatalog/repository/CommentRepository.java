package mate.cafecatalog.repository;

import java.util.List;
import mate.cafecatalog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCafeId(Long cafeId);

    List<Comment> findAllByUserId(Long userId);

    @Query("SELECT AVG(c.rating) FROM Comment c"
            + " WHERE c.cafe.id = :cafeId")
    Integer getAvgRatingByCafeId(Long cafeId);
}
