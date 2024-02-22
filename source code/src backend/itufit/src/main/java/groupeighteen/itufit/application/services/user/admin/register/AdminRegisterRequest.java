package groupeighteen.itufit.application.services.user.admin.register;

import lombok.Getter;

@Getter
public class AdminRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String adminKey;
}
