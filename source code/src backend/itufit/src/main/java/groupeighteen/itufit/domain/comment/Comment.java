package groupeighteen.itufit.domain.comment;

import java.time.LocalDateTime;

import groupeighteen.itufit.domain.facility.Facility;
import groupeighteen.itufit.domain.user.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String comment;
    private Integer rating;

    @ManyToOne
    private Student student;
    @ManyToOne
    private Facility facility;
}
