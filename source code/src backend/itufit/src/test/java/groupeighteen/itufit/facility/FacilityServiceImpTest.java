package groupeighteen.itufit.facility;

import groupeighteen.itufit.application.persistence.repositories.FacilityRepository;
import groupeighteen.itufit.application.services.facility.*;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.domain.facility.Facility;
import groupeighteen.itufit.domain.facility.FacilityType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FacilityServiceImpTest {
    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private FacilityServiceImp facilityService;

    @Test
    public void testAddFacilitySuccessfully() {
        // Arrange
        FacilityAddRequest request = new FacilityAddRequest();
        request.setCapacity(10);
        request.setDescription("Test Facility");
        request.setFacilityType(FacilityType.GYM);
        request.setLocation("Test Location");

        // Act
        IResponse response = facilityService.add(request);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("", response.getMessage());
    }

    @Test
    public void testAddFacilityRepositoryException() {
        // Arrange
        FacilityAddRequest request = new FacilityAddRequest();
        request.setCapacity(10);
        request.setDescription("Test Facility");
        request.setFacilityType(FacilityType.GYM);
        request.setLocation("Test Location");

        when(facilityRepository.save(Mockito.any())).thenThrow(new RuntimeException("Test Exception"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> facilityService.add(request));
    }
    @Test
    public void testRemoveFacilitySuccessfully() {
        // Arrange
        FacilityRemoveRequest request = new FacilityRemoveRequest();
        request.setId(1L);

        Optional<Facility> optionalFacility = Optional.of(new Facility());
        when(facilityRepository.findById(1L)).thenReturn(optionalFacility);

        // Act
        IResponse response = facilityService.remove(request);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("", response.getMessage());
    }

    @Test
    public void testRemoveFacilityNotFound() {
        // Arrange
        FacilityRemoveRequest request = new FacilityRemoveRequest();
        request.setId(1L);

        when(facilityRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> facilityService.remove(request));
    }

    @Test
    public void testListFacilitiesSuccessfully() {
        // Arrange
        List<Facility> facilities = new ArrayList<>();
        facilities.add(new Facility());
        when(facilityRepository.findAll()).thenReturn(facilities);

        // Act
        IDataResponse<List<FacilityListResponse>> response = facilityService.list();

        // Assert
        assertTrue(response.getData().size() > 0);
    }

    @Test
    public void testListNoFacilitiesFound() {
        // Arrange
        when(facilityRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        IDataResponse<List<FacilityListResponse>> response = facilityService.list();

        // Assert
        assertTrue(response.getData().isEmpty());
        assertEquals("There are no facility exists.", response.getMessage());
    }

    @Test
    public void testSearchFacilitySuccessfully() {
        // Arrange
        FacilitySearchRequest request = new FacilitySearchRequest();
        request.setFacilityType(FacilityType.GYM);

        Facility mockFacility = new Facility();
        mockFacility.setId(1L);
        mockFacility.setFacilityType(FacilityType.GYM);

        when(facilityRepository.findByFacilityType(FacilityType.GYM)).thenReturn(Optional.of(mockFacility));

        // Act
        IDataResponse<FacilityListResponse> response = facilityService.search(request);

        // Assert
        assertTrue(response.getData().getFacilityId() > 0);
        assertEquals("", response.getMessage());
    }

    @Test
    public void testSearchFacilityNotFound() {
        // Arrange
        FacilitySearchRequest request = new FacilitySearchRequest();
        request.setFacilityType(FacilityType.GYM);

        when(facilityRepository.findByFacilityType(FacilityType.GYM)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> facilityService.search(request));
    }

    @Test
    public void testFindByFacilityTypeSuccessfully() {
        // Arrange
        FacilityType testType = FacilityType.GYM;

        Facility mockFacility = new Facility();
        mockFacility.setId(1L);
        mockFacility.setFacilityType(testType);

        when(facilityRepository.findByFacilityType(testType)).thenReturn(Optional.of(mockFacility));

        // Act
        Facility result = facilityService.findByFacilityType(testType);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testFindByFacilityTypeNotFound() {
        // Arrange
        FacilityType nonExistingType = FacilityType.GYM;

        when(facilityRepository.findByFacilityType(nonExistingType)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> facilityService.findByFacilityType(nonExistingType));
    }
}
