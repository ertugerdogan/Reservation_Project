package groupeighteen.itufit.facility;

import groupeighteen.itufit.application.services.facility.FacilityAddRequest;
import groupeighteen.itufit.application.services.facility.FacilityRemoveRequest;
import groupeighteen.itufit.application.services.facility.FacilityService;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.domain.facility.FacilityType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class FacilityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacilityService facilityService;

    @Test
    public void testAddFacilityEndpoint() throws Exception {
        FacilityAddRequest request = new FacilityAddRequest();
        request.setCapacity(50);
        request.setDescription("Test Facility");
        request.setFacilityType(FacilityType.GYM);
        request.setLocation("Test Location");

        IResponse mockResponse = new DataResponse<>(true, "Facility added successfully", null);
        when(facilityService.add(request)).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/facilities/addFacility")
                        .contentType("application/json")
                        .content("{ \"capacity\": 50, \"description\": \"Test Facility\", \"facilityType\": \"Test Type\", \"location\": \"Test Location\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Facility added successfully"));
    }

    @Test
    public void testRemoveFacilityEndpoint() throws Exception {
        FacilityRemoveRequest request = new FacilityRemoveRequest();
        request.setId(1L);

        IResponse mockResponse = new DataResponse<>(true, "Facility removed successfully", null);
        when(facilityService.remove(request)).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/facilities/removeFacility")
                        .contentType("application/json")
                        .content("{ \"id\": 1 }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Facility removed successfully"));
    }

}

