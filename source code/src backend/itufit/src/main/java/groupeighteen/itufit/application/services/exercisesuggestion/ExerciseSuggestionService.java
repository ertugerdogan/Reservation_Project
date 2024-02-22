package groupeighteen.itufit.application.services.exercisesuggestion;

import groupeighteen.itufit.application.services.exercisesuggestion.suggestionrequest.ExerciseSuggestionRequest;
import groupeighteen.itufit.application.services.exercisesuggestion.suggestionrequest.ExerciseSuggestionResponse;
import groupeighteen.itufit.application.services.facility.FacilityAddRequest;
import groupeighteen.itufit.application.services.facility.FacilityListResponse;
import groupeighteen.itufit.application.services.facility.FacilityRemoveRequest;
import groupeighteen.itufit.application.services.facility.FacilitySearchRequest;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.domain.facility.Facility;

import java.util.List;

public interface ExerciseSuggestionService {
    IDataResponse<List<ExerciseSuggestionResponse>> suggestExercise(ExerciseSuggestionRequest exerciseSuggestionRequest);
}