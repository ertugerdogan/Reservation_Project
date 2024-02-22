package groupeighteen.itufit.application.services.user.admin.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminLoginResponse {
    private String jwtToken;
}
