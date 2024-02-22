package groupeighteen.itufit.application.services.reservation.list;

import java.time.LocalDateTime;

import groupeighteen.itufit.domain.facility.FacilityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationListResponse {
    private Long reservationId;
    private FacilityType facilityType;
    private LocalDateTime date;
}
