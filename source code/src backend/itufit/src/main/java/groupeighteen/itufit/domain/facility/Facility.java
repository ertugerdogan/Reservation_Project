package groupeighteen.itufit.domain.facility;

import groupeighteen.itufit.domain.comment.Comment;
import groupeighteen.itufit.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private FacilityType facilityType;
    private Integer capacity;
    private String description;
    private String location;
    @OneToMany(mappedBy = "facility")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "facility")
    private List<Comment> comments;
    public int getCalorie() {
        return FacilityConstants.getCalorie(this.facilityType.toString());
    }
}
