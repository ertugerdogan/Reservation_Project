package groupeighteen.itufit.application.services.notification.get;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetNotificationsRequest {
    private LocalDateTime requestTime;
}
