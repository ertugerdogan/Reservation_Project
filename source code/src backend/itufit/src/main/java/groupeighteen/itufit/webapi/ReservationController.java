package groupeighteen.itufit.webapi;

import groupeighteen.itufit.application.services.reservation.ReservationService;
import groupeighteen.itufit.application.services.reservation.delete.ReservationDeleteRequest;
import groupeighteen.itufit.application.services.reservation.list.ReservationListRequest;
import groupeighteen.itufit.application.services.reservation.list.ReservationListResponse;
import groupeighteen.itufit.application.services.reservation.make.ReservationMakeRequest;
import groupeighteen.itufit.application.services.reservation.sessionAvailable.ReservationSessionAvailableRequest;
import groupeighteen.itufit.application.services.reservation.sessionAvailable.ReservationSessionAvailableResponse;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(value = "makeReservation", produces = "application/json")
    public DataResponse<Boolean> make(@RequestBody ReservationMakeRequest reservationMakeRequest) {
        return reservationService.make(reservationMakeRequest);
    }

    @PostMapping(value = "deleteReservation", produces = "application/json")
    public IResponse delete(@RequestBody ReservationDeleteRequest reservationDeleteRequest) throws Exception {
        return reservationService.delete(reservationDeleteRequest);
    }

    // TODO: Check functionallity of sessionAvaliable
    @GetMapping(value = "sessionAvailable", produces = "application/json")
    public DataResponse<ReservationSessionAvailableResponse> sessionAvailable(@RequestBody ReservationSessionAvailableRequest reservationSessionAvailableRequest) throws Exception {
        return reservationService.sessionAvailable(reservationSessionAvailableRequest);
    }

    @PostMapping(value = "listReservation", produces = "application/json")
    public IDataResponse<List<ReservationListResponse>> list(@RequestBody ReservationListRequest reservationListRequest) {
        return reservationService.list(reservationListRequest);
    }


}
