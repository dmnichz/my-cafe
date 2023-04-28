package mate.cafecatalog.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import mate.cafecatalog.security.token.Token;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    // todo implement own constraints for email and password
    private String password;
    @Column(unique = true, nullable = false)
    private String username;
    @ManyToMany
    private List<Cafe> favourites = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    @OneToMany
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
}
