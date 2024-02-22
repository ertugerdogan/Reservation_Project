package groupeighteen.itufit.application.services.reservation.sessionAvailable;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationSessionAvailableRequest {
    private Long facilityId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
