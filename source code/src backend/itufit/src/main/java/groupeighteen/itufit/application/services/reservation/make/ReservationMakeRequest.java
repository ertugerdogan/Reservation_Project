package groupeighteen.itufit.application.services.reservation.make;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationMakeRequest {
    private Long facilityId;
    private Long userId;
    private String startTime;
    private String date;
}
