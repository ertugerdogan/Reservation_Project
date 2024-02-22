package groupeighteen.itufit.user;

import groupeighteen.itufit.application.services.user.student.login.StudentLoginRequest;
import groupeighteen.itufit.application.services.user.student.login.StudentLoginResponse;
import groupeighteen.itufit.application.services.user.student.register.StudentRegisterRequest;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.webapi.StudentController;
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
public class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentController studentController;

    @Test
    public void testLoginEndpoint() throws Exception {
        StudentLoginRequest request = new StudentLoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("test123");

        StudentLoginResponse mockResponse = new StudentLoginResponse("token", 1L);
        when(studentController.login(request)).thenReturn(new DataResponse<>(true, "", mockResponse));

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/students/login")
                        .contentType("application/json")
                        .content("{ \"email\": \"test@example.com\", \"password\": \"test123\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.token").value("token"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1));
    }

    @Test
    public void testRegisterEndpoint() throws Exception {
        StudentRegisterRequest request = new StudentRegisterRequest();
        request.setEmail("test@example.com");
        request.setFirstName("Test");
        request.setLastName("User");
        request.setPassword("test123");

        IResponse mockResponse = new DataResponse<>(true, "User registered successfully", null);
        when(studentController.register(request)).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/students/register")
                        .contentType("application/json")
                        .content("{ \"email\": \"test@example.com\", \"firstName\": \"Test\", \"lastName\": \"User\", \"password\": \"test123\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User registered successfully"));
    }
}
