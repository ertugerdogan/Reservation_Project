package groupeighteen.itufit.webapi;

import groupeighteen.itufit.application.services.exercisesuggestion.ExerciseSuggestionService;
import groupeighteen.itufit.application.services.exercisesuggestion.suggestionrequest.ExerciseSuggestionRequest;
import groupeighteen.itufit.application.services.exercisesuggestion.suggestionrequest.ExerciseSuggestionResponse;
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

import java.util.List;

@RestController
@RequestMapping("exercisesuggestion")
public class ExerciseSuggestionController {
    private final ExerciseSuggestionService exerciseSuggestionService;

    public ExerciseSuggestionController(ExerciseSuggestionService exerciseSuggestionService) {
        this.exerciseSuggestionService = exerciseSuggestionService;
    }

    @PostMapping(value = "suggest", produces = "application/json")
    public IDataResponse<List<ExerciseSuggestionResponse>> suggest(@RequestBody ExerciseSuggestionRequest exerciseSuggestionRequest) {
        return exerciseSuggestionService.suggestExercise(exerciseSuggestionRequest);
    }
}

