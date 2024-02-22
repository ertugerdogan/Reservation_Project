package groupeighteen.itufit.user;

import groupeighteen.itufit.application.persistence.repositories.StudentRepository;
import groupeighteen.itufit.application.security.HashService;
import groupeighteen.itufit.application.security.JwtService;
import groupeighteen.itufit.application.services.user.student.StudentServiceImp;
import groupeighteen.itufit.application.services.user.student.login.StudentLoginRequest;
import groupeighteen.itufit.application.services.user.student.login.StudentLoginResponse;
import groupeighteen.itufit.application.services.user.student.physicalinfo.StudentGetPhysicalInfoRequest;
import groupeighteen.itufit.application.services.user.student.physicalinfo.StudentGetPhysicalInfoRespond;
import groupeighteen.itufit.application.services.user.student.physicalinfo.StudentSetPhysicalInfoRequest;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.domain.user.Student;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentServiceImpTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private HashService hashService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private StudentServiceImp studentService;

    @Test
    public void testLoginSuccessfully() {
        StudentLoginRequest request = new StudentLoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("testPassword");

        Student mockStudent = new Student();
        mockStudent.setId(1L);
        mockStudent.setEmail("test@example.com");
        mockStudent.setPasswordSalt("testSalt".getBytes());
        mockStudent.setPasswordHash("testHash".getBytes());

        when(studentRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockStudent));
        when(hashService.hashPassword("testPassword", "testSalt".getBytes())).thenReturn("testHash".getBytes());
        when(jwtService.generateToken("test@example.com")).thenReturn("testToken");

        IDataResponse<StudentLoginResponse> response = studentService.login(request);

        assertTrue(response.getData().getJwtToken() != null && response.getData().getUserId() != null);
        assertEquals("", response.getMessage());
    }

    @Test
    public void testSetPhysicalInfoSuccessfully() {
        StudentSetPhysicalInfoRequest request = new StudentSetPhysicalInfoRequest();
        request.setId(1L);
        request.setWeight(70.0);
        request.setHeight(175.0);
        request.setGoalWeight(65.0);
        request.setBasalMetabolism(1800);

        Student mockStudent = new Student();
        mockStudent.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));

        IResponse response = studentService.setPhysicalInfo(request);

        assertTrue(response.isSuccess());
        assertEquals("", response.getMessage());
    }

    @Test
    public void testSetPhysicalInfoFailed() {
        StudentSetPhysicalInfoRequest request = new StudentSetPhysicalInfoRequest();
        request.setId(2L);  // Assuming student with ID 2 does not exist

        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.setPhysicalInfo(request));
    }

    @Test
    public void testGetPhysicalInfoSuccessfully() {
        StudentGetPhysicalInfoRequest request = new StudentGetPhysicalInfoRequest();
        request.setUserId(1L);

        Student mockStudent = new Student();
        mockStudent.setId(1L);
        mockStudent.setWeight(70.0);
        mockStudent.setHeight(175.0);
        mockStudent.setGoalWeight(65.0);
        mockStudent.setBasalMetabolism(1800);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));

        IDataResponse<StudentGetPhysicalInfoRespond> response = studentService.getPhysicalInfo(request);

        assertTrue(response.isSuccess());
        assertEquals("Physical Info returned", response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    public void testGetPhysicalInfoFailed() {
        StudentGetPhysicalInfoRequest request = new StudentGetPhysicalInfoRequest();
        request.setUserId(2L);  // Assuming student with ID 2 does not exist

        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.getPhysicalInfo(request));
    }

    @Test
    public void testLoginFail(){
        StudentLoginRequest request = new StudentLoginRequest();
        request.setEmail("nonexistent@example.com");
        request.setPassword("invalidPassword");

        when(studentRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        IDataResponse<StudentLoginResponse> response = studentService.login(request);

        assertTrue(!response.isSuccess());
        assertEquals("Email or password incorrect!", response.getMessage());
    }
}
