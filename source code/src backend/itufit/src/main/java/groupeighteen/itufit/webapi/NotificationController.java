package groupeighteen.itufit.webapi;

import groupeighteen.itufit.application.services.comment.CommentAddRequest;
import groupeighteen.itufit.application.services.comment.CommentService;
import groupeighteen.itufit.application.services.notification.NotificationService;
import groupeighteen.itufit.application.services.notification.get.GetNotificationsRequest;
import groupeighteen.itufit.application.services.user.student.login.StudentLoginResponse;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.domain.notification.Notification;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @CrossOrigin
    @PostMapping(value = "getNotifications", produces = "application/json")
    public IDataResponse<List<Notification>> add(@RequestBody GetNotificationsRequest getNotificationsRequest) {
        return notificationService.getNotifications(getNotificationsRequest);
    }

}
