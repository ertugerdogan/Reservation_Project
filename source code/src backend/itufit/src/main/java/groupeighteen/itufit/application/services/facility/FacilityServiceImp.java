package groupeighteen.itufit.application.services.facility;

import java.util.ArrayList;
import java.util.List;

// import java.time.LocalDateTime;
// import java.time.LocalTime;


import org.springframework.stereotype.Service;
// import java.time.LocalDateTime;

import groupeighteen.itufit.application.persistence.repositories.FacilityRepository;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.application.shared.response.Response;
import groupeighteen.itufit.domain.facility.Facility;
// import groupeighteen.itufit.domain.user.User;
import groupeighteen.itufit.domain.facility.FacilityType;

@Service
public class FacilityServiceImp implements FacilityService {
    private FacilityRepository facilityRepository;


    public FacilityServiceImp(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public IResponse add(FacilityAddRequest facilityAddRequest) {
        Facility facilityToAdd = new Facility();

        facilityToAdd.setCapacity(facilityAddRequest.getCapacity());
        facilityToAdd.setDescription(facilityAddRequest.getDescription());
        facilityToAdd.setFacilityType(facilityAddRequest.getFacilityType());
        facilityToAdd.setLocation(facilityAddRequest.getLocation());

        facilityRepository.save(facilityToAdd);
        return new Response<>(true, "");
    }

    public IResponse remove(FacilityRemoveRequest facilityRemoveRequest) {
        var optionalFacility = facilityRepository.findById(facilityRemoveRequest.getId());
        if (optionalFacility.isEmpty())
            throw new RuntimeException("");

        Facility facilityToRemove = optionalFacility.get();

        facilityRepository.delete(facilityToRemove);
        return new Response<>(true, "");
    }

    public IDataResponse<List<FacilityListResponse>> list() {
        List<Facility> facilities = facilityRepository.findAll();
        List<FacilityListResponse> facilityIds = new ArrayList<>();

        for (Facility facility : facilities) {
            FacilityListResponse aFacility = new FacilityListResponse(facility.getId(), facility.getFacilityType());

            // aFacility.setFacilityId(facility.getId());
            // aFacility.setFacilityType(facility.getFacilityType());
            facilityIds.add(aFacility);
        }

        String massage;

        if (facilityIds.isEmpty())
            massage = "There are no facility exists.";
        else
            massage = "";

        var response = new DataResponse<List<FacilityListResponse>>(true, massage, facilityIds);
        return response;
    }

    public IDataResponse<FacilityListResponse> search(FacilitySearchRequest facilitySearchRequest) {
        var optionalFacility = facilityRepository.findByFacilityType(facilitySearchRequest.getFacilityType());
        if (optionalFacility.isEmpty())
            throw new RuntimeException();
        Facility facility = optionalFacility.get();
        FacilityListResponse response = new FacilityListResponse(facility.getId(), facility.getFacilityType());
        var dataResponse = new DataResponse<FacilityListResponse>(true, "", response);
        return dataResponse;
    }

    @Override
    public Facility findByFacilityType(FacilityType facilityType) {
        var optionalFacility = facilityRepository.findByFacilityType(facilityType);
        if (optionalFacility.isEmpty())
            throw new RuntimeException();
        return optionalFacility.get();
    }

    public Facility findById(Long id) {
        var optionalFacility = facilityRepository.findById(id);
        if (optionalFacility.isEmpty())
            throw new RuntimeException();
        return optionalFacility.get();
    }
}
