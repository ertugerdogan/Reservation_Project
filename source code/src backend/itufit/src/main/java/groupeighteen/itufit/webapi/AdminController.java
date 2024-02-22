package groupeighteen.itufit.webapi;

import groupeighteen.itufit.application.services.user.admin.AdminService;
import groupeighteen.itufit.application.services.user.admin.changepassword.AdminPasswordChangeRequest;
import groupeighteen.itufit.application.services.user.admin.login.AdminLoginRequest;
import groupeighteen.itufit.application.services.user.admin.login.AdminLoginResponse;
import groupeighteen.itufit.application.services.user.admin.register.AdminRegisterRequest;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "login", produces = "application/json")
    public IDataResponse<AdminLoginResponse> login(@RequestBody AdminLoginRequest adminLoginRequest) throws Exception {
        return adminService.login(adminLoginRequest);
    }

    @PostMapping(value = "register", produces = "application/json")
    public IResponse register(@RequestBody AdminRegisterRequest adminRegisterRequest) {
        return adminService.register(adminRegisterRequest);
    }

    @PostMapping(value = "changepassword", produces = "application/json")
    public IResponse changePassword(@RequestBody AdminPasswordChangeRequest adminPasswordChangeRequest) {
        return adminService.changePassword(adminPasswordChangeRequest);
    }
}

