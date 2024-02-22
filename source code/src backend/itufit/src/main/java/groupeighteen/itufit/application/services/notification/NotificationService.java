package groupeighteen.itufit.application.services.notification;

import groupeighteen.itufit.application.services.notification.create.CreateNotificationRequest;
import groupeighteen.itufit.application.services.notification.delete.DeleteNotificationRequest;
import groupeighteen.itufit.application.services.notification.get.GetNotificationsRequest;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.domain.notification.Notification;

import java.util.List;

public interface NotificationService {
    void createNotification(CreateNotificationRequest createNotificationRequest);

    void deleteNotification(DeleteNotificationRequest deleteNotificationRequest);

    IDataResponse<List<Notification>> getNotifications(GetNotificationsRequest getNotificationsRequest);
}
