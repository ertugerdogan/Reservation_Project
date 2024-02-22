package groupeighteen.itufit.application.services.facility;

import groupeighteen.itufit.domain.facility.FacilityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilitySearchRequest {
    FacilityType facilityType;
}
