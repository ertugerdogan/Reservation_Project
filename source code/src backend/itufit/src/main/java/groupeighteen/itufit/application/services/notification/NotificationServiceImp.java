package groupeighteen.itufit.application.services.notification;

import groupeighteen.itufit.application.persistence.repositories.NotificationRepository;
import groupeighteen.itufit.application.services.notification.create.CreateNotificationRequest;
import groupeighteen.itufit.application.services.notification.delete.DeleteNotificationRequest;
import groupeighteen.itufit.application.services.notification.get.GetNotificationsRequest;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.domain.notification.Notification;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImp implements NotificationService {
    public static final int[] notificationIntervals = {24, 4, 1};

    private final NotificationRepository notificationRepository;

    public NotificationServiceImp(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(CreateNotificationRequest createNotificationRequest) {
        for (int notificationInterval : notificationIntervals) {
            var notification = new Notification();
            notification.setMessage("Reservasyona kaldı.");
            notification.setReservation(createNotificationRequest.getReservation());
            notification.setNotificationTime(createNotificationRequest.getReservation().getStartTime().plusHours(notificationInterval));
            this.notificationRepository.save(notification);
        }
    }

    @Transactional
    public void deleteNotification(DeleteNotificationRequest deleteNotificationRequest) {
        this.notificationRepository.deleteNotificationsByReservationId(deleteNotificationRequest.getReservation().getId());
    }

    @Override
    public IDataResponse<List<Notification>> getNotifications(GetNotificationsRequest getNotificationsRequest) {
        var notifications = new ArrayList<Notification>();
        for (int notificationInterval : notificationIntervals) {
            var foundNotifications = this.notificationRepository.getNotificationsByNotificationTime(getNotificationsRequest.getRequestTime().plusHours(notificationInterval));
            if (!foundNotifications.isPresent())
                continue;
            notifications.addAll(foundNotifications.get());
        }
        return new DataResponse<>(true, "", notifications);
    }
}