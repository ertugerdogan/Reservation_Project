package groupeighteen.itufit.domain.notification;

import groupeighteen.itufit.domain.reservation.Reservation;
import groupeighteen.itufit.domain.user.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Reservation reservation;
    private String message;
    private LocalDateTime notificationTime;
}
