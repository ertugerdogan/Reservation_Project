package groupeighteen.itufit.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import groupeighteen.itufit.application.services.reservation.ReservationService;
import groupeighteen.itufit.application.services.reservation.delete.ReservationDeleteRequest;
import groupeighteen.itufit.application.services.reservation.make.ReservationMakeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationService reservationService;

    @Test
    public void testMakeReservationIntegration() throws Exception {
        ReservationMakeRequest request = new ReservationMakeRequest();
        request.setUserId(1L);
        request.setFacilityId(1l);
        request.setStartTime("10:00:00");
        request.setDate("2024-01-01");

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/reservations/makeReservation")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(""));
    }

    @Test
    public void testDeleteReservationIntegration() throws Exception {
        ReservationDeleteRequest request = new ReservationDeleteRequest();
        request.setReservationId(1L);
        request.setUserId(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/reservations/deleteReservation")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(""));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

