package groupeighteen.itufit.webapi;

import org.springframework.web.bind.annotation.RestController;

import groupeighteen.itufit.application.services.facility.FacilityAddRequest;
import groupeighteen.itufit.application.services.facility.FacilityListResponse;
import groupeighteen.itufit.application.services.facility.FacilityRemoveRequest;
import groupeighteen.itufit.application.services.facility.FacilitySearchRequest;
import groupeighteen.itufit.application.services.facility.FacilityService;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("facilities")
public class FacilityController {
    
    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @PostMapping(value = "addFacility", produces = "application/json")
    public IResponse add(@RequestBody FacilityAddRequest facilityAddRequest) throws Exception{
        return facilityService.add(facilityAddRequest);
    }

    @PostMapping(value = "removeFacility", produces = "application/json")
    public IResponse remove(@RequestBody FacilityRemoveRequest facilityRemoveRequest) throws Exception{
        return facilityService.remove(facilityRemoveRequest);
    }

    @PostMapping(value = "listFacility", produces = "application/json")
    public IDataResponse<List<FacilityListResponse>> list() throws Exception{
        return facilityService.list();
    }

    @PostMapping(value = "searchFacility", produces = "application/json")
    public IDataResponse<FacilityListResponse> search(@RequestBody FacilitySearchRequest facilitySearchRequest) throws Exception {
        return facilityService.search(facilitySearchRequest);
    }
    
}
