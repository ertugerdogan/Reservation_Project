package groupeighteen.itufit.application.services.notification.create;

import groupeighteen.itufit.domain.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateNotificationRequest {
    private Reservation reservation;
}
