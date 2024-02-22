package groupeighteen.itufit.application.services.user.student.changepassword;

import lombok.Getter;

@Getter
public class StudentPasswordChangeRequest {
    private Long userId;
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;
}
