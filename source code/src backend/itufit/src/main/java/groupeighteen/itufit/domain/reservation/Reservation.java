package groupeighteen.itufit.domain.reservation;

import java.time.LocalDateTime;
import java.util.List;

import groupeighteen.itufit.domain.facility.Facility;
import groupeighteen.itufit.domain.notification.Notification;
import groupeighteen.itufit.domain.user.Student;
import groupeighteen.itufit.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    //private Long userId; // toDO: Check if the user id type is correctly defined
    @ManyToOne()
    private Student student;
    @ManyToOne
    private Facility facility; // toDO: Check if the facility id type is correctly defined
    @OneToMany(mappedBy = "reservation")
    private List<Notification> notifications;
}
