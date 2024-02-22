package groupeighteen.itufit.reservation;

import groupeighteen.itufit.application.persistence.repositories.ReservationRepository;
import groupeighteen.itufit.application.services.facility.FacilityService;
import groupeighteen.itufit.application.services.notification.NotificationService;
import groupeighteen.itufit.application.services.reservation.ReservationServiceImp;
import groupeighteen.itufit.application.services.reservation.delete.ReservationDeleteRequest;
import groupeighteen.itufit.application.services.reservation.list.ReservationListRequest;
import groupeighteen.itufit.application.services.reservation.list.ReservationListResponse;
import groupeighteen.itufit.application.services.reservation.make.ReservationMakeRequest;
import groupeighteen.itufit.application.services.reservation.sessionAvailable.ReservationSessionAvailableRequest;
import groupeighteen.itufit.application.services.reservation.sessionAvailable.ReservationSessionAvailableResponse;
import groupeighteen.itufit.application.services.user.student.StudentService;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.domain.facility.Facility;
import groupeighteen.itufit.domain.facility.FacilityType;
import groupeighteen.itufit.domain.reservation.Reservation;
import groupeighteen.itufit.domain.user.Student;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservationServiceImpTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private FacilityService facilityService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private ReservationServiceImp reservationService;

    @Test
    public void testMakeReservationFacilityFull() {
        ReservationMakeRequest request = new ReservationMakeRequest();
        request.setStartTime("2024-01-05T10:00:00");
        request.setDate("2024-01-05");
        request.setFacilityId(1L);
        request.setUserId(1L);

        Facility mockFacility = new Facility();
        mockFacility.setCapacity(1);

        Student mockStudent = new Student();
        mockStudent.setRestricted(false);

        List<Reservation> existingReservations = new ArrayList<>();
        existingReservations.add(new Reservation());

        when(facilityService.findById(1L)).thenReturn(mockFacility);
        when(studentService.findById(1L)).thenReturn(mockStudent);
        when(reservationRepository.findByStartTimeAndFacilityId(Mockito.any(LocalDateTime.class), Mockito.eq(1L))).thenReturn(existingReservations);

        assertThrows(RuntimeException.class, () -> reservationService.make(request));
    }

    @Test
    public void testMakeReservationRestrictedStudent() {
        ReservationMakeRequest request = new ReservationMakeRequest();
        request.setStartTime("2024-01-05T10:00:00");
        request.setDate("2024-01-05");
        request.setFacilityId(1L);
        request.setUserId(1L);

        Facility mockFacility = new Facility();
        mockFacility.setCapacity(10);

        Student mockStudent = new Student();
        mockStudent.setRestricted(true);

        when(facilityService.findById(1L)).thenReturn(mockFacility);
        when(studentService.findById(1L)).thenReturn(mockStudent);

        assertThrows(RuntimeException.class, () -> reservationService.make(request));
    }

    @Test
    public void testMakeReservationSuccessfully() {
        ReservationMakeRequest request = new ReservationMakeRequest();
        request.setStartTime("10:00:00");
        request.setDate("2024-01-05");
        request.setFacilityId(1L);
        request.setUserId(1L);

        Facility mockFacility = new Facility();
        mockFacility.setCapacity(10);

        Student mockStudent = new Student();
        mockStudent.setRestricted(false);

        when(facilityService.findById(1L)).thenReturn(mockFacility);
        when(studentService.findById(1L)).thenReturn(mockStudent);
        when(reservationRepository.findByStartTimeAndFacilityId(Mockito.any(LocalDateTime.class), Mockito.eq(1L))).thenReturn(new ArrayList<>());

        DataResponse<Boolean> response = reservationService.make(request);

        assertTrue(response.getData());
    }

    @Test
    public void testDeleteReservationSuccessfully() {
        ReservationDeleteRequest request = new ReservationDeleteRequest();
        request.setReservationId(1L);
        request.setUserId(1L);

        Reservation mockReservation = new Reservation();

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(mockReservation));

        IResponse response = reservationService.delete(request);

        assertTrue(response.isSuccess());
    }

    @Test
    public void testDeleteNonexistentReservation() {
        ReservationDeleteRequest request = new ReservationDeleteRequest();
        request.setReservationId(1L);
        request.setUserId(1L);

        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationService.delete(request));
    }

    @Test
    public void testSessionAvailableSuccessfully() {
        ReservationSessionAvailableRequest request = new ReservationSessionAvailableRequest();
        request.setStartTime(LocalDateTime.now());
        request.setFacilityId(1L);

        Facility mockFacility = new Facility();
        mockFacility.setCapacity(10);

        when(facilityService.findById(1L)).thenReturn(mockFacility);
        when(reservationRepository.findByStartTimeAndFacilityId(Mockito.any(LocalDateTime.class), Mockito.eq(1L))).thenReturn(new ArrayList<>());

        DataResponse<ReservationSessionAvailableResponse> response = reservationService.sessionAvailable(request);

        assertTrue(response.isSuccess());
    }

    @Test
    public void testSessionAvailableFullCapacity() {
        ReservationSessionAvailableRequest request = new ReservationSessionAvailableRequest();
        request.setStartTime(LocalDateTime.now());
        request.setFacilityId(1L);

        Facility mockFacility = new Facility();
        mockFacility.setCapacity(1);

        List<Reservation> existingReservations = new ArrayList<>();
        existingReservations.add(new Reservation());

        when(facilityService.findById(1L)).thenReturn(mockFacility);
        when(reservationRepository.findByStartTimeAndFacilityId(Mockito.any(LocalDateTime.class), Mockito.eq(1L))).thenReturn(existingReservations);

        assertThrows(RuntimeException.class, () -> reservationService.sessionAvailable(request));
    }

    @Test
    public void testIsSessionAvailableSuccessfully() {
        LocalDateTime startTime = LocalDateTime.now();
        Long facilityId = 1L;

        Facility mockFacility = new Facility();
        mockFacility.setCapacity(10);

        when(facilityService.findById(1L)).thenReturn(mockFacility);
        when(reservationRepository.findByStartTimeAndFacilityId(Mockito.any(LocalDateTime.class), Mockito.eq(1L))).thenReturn(new ArrayList<>());

        boolean result = reservationService.isSessionAvailable(facilityId, startTime);

        assertTrue(result);
    }

    @Test
    public void testIsSessionAvailableFullCapacity() {
        LocalDateTime startTime = LocalDateTime.now();
        Long facilityId = 1L;

        Facility mockFacility = new Facility();
        mockFacility.setCapacity(1);

        List<Reservation> existingReservations = new ArrayList<>();
        existingReservations.add(new Reservation());

        when(facilityService.findById(1L)).thenReturn(mockFacility);
        when(reservationRepository.findByStartTimeAndFacilityId(Mockito.any(LocalDateTime.class), Mockito.eq(1L))).thenReturn(existingReservations);

        boolean result = reservationService.isSessionAvailable(facilityId, startTime);

        assertFalse(result);
    }

    @Test
    public void testListReservationsSuccessfully() {
        ReservationListRequest request = new ReservationListRequest();
        request.setUserId(1L);

        Student mockStudent = new Student();
        mockStudent.setId(1L);

        Facility mockFacility = new Facility();
        mockFacility.setFacilityType(FacilityType.GYM);

        List<Reservation> reservations = new ArrayList<>();
        Reservation mockReservation = new Reservation();
        mockReservation.setFacility(mockFacility);
        reservations.add(mockReservation);

        when(studentService.findById(1L)).thenReturn(mockStudent);
        when(reservationRepository.findByStudent(mockStudent)).thenReturn(reservations);

        when(facilityService.findById(Mockito.anyLong())).thenReturn(mockFacility);

        IDataResponse<List<ReservationListResponse>> response = reservationService.list(request);

        assertTrue(response.getData().size() > 0);
        assertEquals(FacilityType.GYM, response.getData().get(0).getFacilityType());
    }

    @Test
    public void testListNoReservationsFound() {
        ReservationListRequest request = new ReservationListRequest();
        request.setUserId(1L);

        Student mockStudent = new Student();
        mockStudent.setId(1L);

        when(studentService.findById(1L)).thenReturn(mockStudent);
        when(reservationRepository.findByStudent(mockStudent)).thenReturn(new ArrayList<>());

        IDataResponse<List<ReservationListResponse>> response = reservationService.list(request);

        assertTrue(response.getData().isEmpty());
    }
}
