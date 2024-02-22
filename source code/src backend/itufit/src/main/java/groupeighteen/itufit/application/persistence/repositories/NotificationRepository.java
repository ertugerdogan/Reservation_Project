package groupeighteen.itufit.application.persistence.repositories;

import groupeighteen.itufit.domain.notification.Notification;
import groupeighteen.itufit.domain.user.AdminKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<List<Notification>> getNotificationsByNotificationTime(LocalDateTime notificationTime);
    void deleteNotificationsByReservationId(Long reservationId);
}

