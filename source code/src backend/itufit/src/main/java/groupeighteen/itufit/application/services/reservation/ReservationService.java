package groupeighteen.itufit.application.services.reservation;

import groupeighteen.itufit.application.services.reservation.delete.ReservationDeleteRequest;
import groupeighteen.itufit.application.services.reservation.list.ReservationListRequest;
import groupeighteen.itufit.application.services.reservation.list.ReservationListResponse;
import groupeighteen.itufit.application.services.reservation.make.ReservationMakeRequest;
import groupeighteen.itufit.application.services.reservation.sessionAvailable.ReservationSessionAvailableRequest;
import groupeighteen.itufit.application.services.reservation.sessionAvailable.ReservationSessionAvailableResponse;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;

import java.time.LocalDateTime;
import java.util.List;

// import groupeighteen.itufit.domain.reservation.Reservation;

public interface ReservationService {
    DataResponse<Boolean> make(ReservationMakeRequest reservationMakeRequest);

    IResponse delete(ReservationDeleteRequest reservationDeleteRequest);

    DataResponse<ReservationSessionAvailableResponse> sessionAvailable(ReservationSessionAvailableRequest reservationSessionAvailableRequest);

    public IDataResponse<List<ReservationListResponse>> list(ReservationListRequest reservationListRequest);

    boolean isSessionAvailable(Long facilityId, LocalDateTime startTime);
}
