package groupeighteen.itufit.application.services.user.student.register;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
