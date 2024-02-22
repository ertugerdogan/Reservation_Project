package groupeighteen.itufit.application.services.reservation.delete;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDeleteRequest {
    private Long reservationId;
    private Long userId;
}
