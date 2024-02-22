package groupeighteen.itufit.application.services.reservation;

import groupeighteen.itufit.application.persistence.repositories.ReservationRepository;
import groupeighteen.itufit.application.services.facility.FacilityService;
import groupeighteen.itufit.application.services.notification.NotificationService;
import groupeighteen.itufit.application.services.notification.create.CreateNotificationRequest;
import groupeighteen.itufit.application.services.notification.delete.DeleteNotificationRequest;
import groupeighteen.itufit.application.services.reservation.sessionAvailable.ReservationSessionAvailableRequest;
import groupeighteen.itufit.application.services.reservation.sessionAvailable.ReservationSessionAvailableResponse;
import groupeighteen.itufit.application.services.reservation.make.ReservationMakeRequest;
import groupeighteen.itufit.application.services.reservation.list.ReservationListRequest;
import groupeighteen.itufit.application.services.reservation.list.ReservationListResponse;
import groupeighteen.itufit.application.services.reservation.delete.ReservationDeleteRequest;
import groupeighteen.itufit.application.services.user.student.StudentService;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.application.shared.response.Response;
import groupeighteen.itufit.domain.reservation.Reservation;
import groupeighteen.itufit.domain.user.Student;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImp implements ReservationService {
    private ReservationRepository reservationRepository;
    private FacilityService facilityService;
    private StudentService studentService;
    private NotificationService notificationService;

    public ReservationServiceImp(ReservationRepository reservationRepository, FacilityService facilityService, StudentService studentService, NotificationService notificationService) {
        this.reservationRepository = reservationRepository;
        this.facilityService = facilityService;
        this.studentService = studentService;
        this.notificationService = notificationService;
    }

    public DataResponse<Boolean> make(ReservationMakeRequest reservationMakeRequest) {

        String timeString = reservationMakeRequest.getStartTime();
        String dateString = reservationMakeRequest.getDate();

        String[] dateParts = dateString.split("-");
        String[] timeParts = timeString.split(":");

        Integer year = Integer.valueOf(dateParts[0]);
        Integer month = Integer.valueOf(dateParts[1]);
        Integer day = Integer.valueOf(dateParts[2]);
        Integer hour = Integer.valueOf(timeParts[0]);

        LocalDateTime localDateTime = LocalDateTime.of(
                year, month, day, hour, 0, 0, 0);

        List<Reservation> reservations = reservationRepository.findByStartTimeAndFacilityId(localDateTime, reservationMakeRequest.getFacilityId());

        Integer capacity = facilityService.findById(reservationMakeRequest.getFacilityId()).getCapacity();
        if (reservations.size() >= capacity)
            throw new RuntimeException("");

        Boolean userIsRestricted = studentService.findById(reservationMakeRequest.getUserId()).isRestricted();
        if (userIsRestricted)
            throw new RuntimeException("");

        Reservation reservationToMake = new Reservation();
        reservationToMake.setStartTime(localDateTime);
        reservationToMake.setFacility(facilityService.findById(reservationMakeRequest.getFacilityId()));
        reservationToMake.setStudent(studentService.findById(reservationMakeRequest.getUserId()));

        reservationRepository.save(reservationToMake);
        this.notificationService.createNotification(new CreateNotificationRequest(reservationToMake));

        studentService.increaseScore(reservationMakeRequest.getUserId());

        var response = new DataResponse<Boolean>(
                true,
                "",
                true);

        return response;
    }

    public IResponse delete(ReservationDeleteRequest reservationDeleteRequest) {

        var optionalReservation = reservationRepository.findById(reservationDeleteRequest.getReservationId());
        if (optionalReservation.isEmpty())
            throw new RuntimeException("");

        Reservation reservationToDelete = optionalReservation.get();

        this.notificationService.deleteNotification(new DeleteNotificationRequest(reservationToDelete));
        reservationRepository.delete(reservationToDelete);


        studentService.decreaseScore(reservationDeleteRequest.getUserId());
        return new Response(true, "");
    }

    public DataResponse<ReservationSessionAvailableResponse> sessionAvailable(ReservationSessionAvailableRequest reservationSessionAvailableRequest) {
        List<Reservation> reservations = reservationRepository.findByStartTimeAndFacilityId(reservationSessionAvailableRequest.getStartTime(), reservationSessionAvailableRequest.getFacilityId());

        Integer capacity = facilityService.findById(reservationSessionAvailableRequest.getFacilityId()).getCapacity();
        Integer reservationCount = reservations.size();

        if (reservationCount == capacity)
            throw new RuntimeException("");

        Boolean isFull = reservationCount == capacity;

        var response = new DataResponse<ReservationSessionAvailableResponse>(
                true,
                "",
                new ReservationSessionAvailableResponse(isFull));

        return response;
    }

    public boolean isSessionAvailable(Long facilityId, LocalDateTime startTime) {
        List<Reservation> reservations = reservationRepository.findByStartTimeAndFacilityId(startTime, facilityId);

        Integer capacity = facilityService.findById(facilityId).getCapacity();
        Integer reservationCount = reservations.size();

        if (reservationCount == capacity)
            return false;

        return true;
    }

    public IDataResponse<List<ReservationListResponse>> list(ReservationListRequest reservationListRequest) {

        Student student = studentService.findById(reservationListRequest.getUserId());
        List<Reservation> reservations = reservationRepository.findByStudent(student);
        List<ReservationListResponse> reservationIds = new ArrayList<>();

        for (Reservation reservation : reservations) {
            reservation.getStartTime();

            ReservationListResponse aReservation = new ReservationListResponse(reservation.getId(), reservation.getFacility().getFacilityType(), reservation.getStartTime());
            reservationIds.add(aReservation);
        }

        var response = new DataResponse<List<ReservationListResponse>>(true, "", reservationIds);
        return response;
    }

}

