package groupeighteen.itufit.application.services.user.student.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentLoginRequest {
    private String email;
    private String password;
}
