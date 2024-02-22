package groupeighteen.itufit.application.services.facility;

import groupeighteen.itufit.domain.facility.FacilityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FacilityListResponse {
    private Long facilityId;
    private FacilityType facilityType;
}
