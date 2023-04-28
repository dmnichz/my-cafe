package mate.cafecatalog.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cafes")
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortDescription;
    @Column(length = 3000)
    private String description;
    private String city;
    private String address;
    private String hours;
    private String priceLevel;
    private Integer minOrder;
    private String noiseLevel;
    private Integer tablesNumber;
    private Double latitude;
    private Double longitude;
    private Boolean veganOption;
    private Boolean alcohol;
    private Boolean eventRoom;
    private Integer rating;
    private String instagramLink;
    private String facebookLink;
    private String websiteLink;
    private String logoLink;
    @ManyToMany
    private Set<Option> options;
    @OneToMany
    @JoinTable(name = "cafes_picture_paths",
            joinColumns = @JoinColumn(name = "cafe_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "picture_path_id",
                    referencedColumnName = "id"))
    private Set<PicturePath> imageLink;
    @OneToMany
    private Set<Comment> comments;
}
