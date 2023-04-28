package mate.cafecatalog.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 3000)
    private String text;
    private Integer rating;
    private LocalDateTime publicityDate = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
