package groupeighteen.itufit.domain.user;

import groupeighteen.itufit.domain.academicprogram.AcademicProgram;
import groupeighteen.itufit.domain.comment.Comment;
import groupeighteen.itufit.domain.notification.Notification;
import groupeighteen.itufit.domain.reservation.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Student extends User {

    private double weight;
    private double goalWeight;
    private double height;
    private double basalMetabolism;
    private boolean isRestricted;
    private Integer exerciseScore = 0;

    @OneToMany(mappedBy = "student")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "student")
    private List<Comment> comments;
    @OneToOne
    private AcademicProgram academicProgram;
}
