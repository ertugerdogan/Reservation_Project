package groupeighteen.itufit.application.services.notification.delete;

import groupeighteen.itufit.domain.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteNotificationRequest {
    private Reservation reservation;
}
