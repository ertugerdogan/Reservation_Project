package groupeighteen.itufit.application.services.facility;

import java.util.List;

import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.domain.facility.Facility;
import groupeighteen.itufit.domain.facility.FacilityType;

public interface FacilityService {
    public IResponse add(FacilityAddRequest facilityAddRequest);

    public IResponse remove(FacilityRemoveRequest facilityRemoveRequest);

    public IDataResponse<List<FacilityListResponse>> list();

    public Facility findById(Long id);

    public IDataResponse<FacilityListResponse> search(FacilitySearchRequest facilitySearchRequest);

    Facility findByFacilityType(FacilityType facilityType);
}