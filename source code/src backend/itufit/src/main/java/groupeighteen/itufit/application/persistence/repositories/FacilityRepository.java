package groupeighteen.itufit.application.persistence.repositories;

import groupeighteen.itufit.domain.facility.Facility;
import groupeighteen.itufit.domain.facility.FacilityType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findByFacilityType(FacilityType facilityType);
}
