package groupeighteen.itufit.application.services.user.admin.changepassword;

import lombok.Getter;

@Getter
public class AdminPasswordChangeRequest {
    private Long userId;
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;
}
