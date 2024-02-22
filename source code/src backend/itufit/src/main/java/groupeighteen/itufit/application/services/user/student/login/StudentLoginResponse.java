package groupeighteen.itufit.application.services.user.student.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentLoginResponse {
    private String jwtToken;
    private Long userId;
}
