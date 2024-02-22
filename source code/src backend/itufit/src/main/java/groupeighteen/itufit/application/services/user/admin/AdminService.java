package groupeighteen.itufit.application.services.user.admin;

import groupeighteen.itufit.application.services.user.admin.changepassword.AdminPasswordChangeRequest;
import groupeighteen.itufit.application.services.user.admin.login.AdminLoginRequest;
import groupeighteen.itufit.application.services.user.admin.login.AdminLoginResponse;
import groupeighteen.itufit.application.services.user.admin.register.AdminRegisterRequest;
import groupeighteen.itufit.application.services.user.student.changepassword.StudentPasswordChangeRequest;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.domain.user.Admin;
import groupeighteen.itufit.domain.user.Student;

public interface AdminService {
    IResponse register(AdminRegisterRequest adminRegisterRequest);
    IDataResponse<AdminLoginResponse> login(AdminLoginRequest adminLoginRequest);
    IResponse changePassword(AdminPasswordChangeRequest adminPasswordChangeRequest);
    Admin findById(Long id);

}
