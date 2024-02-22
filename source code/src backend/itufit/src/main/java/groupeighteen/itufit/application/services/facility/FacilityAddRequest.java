package groupeighteen.itufit.application.services.facility;

import groupeighteen.itufit.domain.facility.FacilityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityAddRequest {
    private FacilityType facilityType;
    private Integer capacity;
    private String description;
    private String location;
}
