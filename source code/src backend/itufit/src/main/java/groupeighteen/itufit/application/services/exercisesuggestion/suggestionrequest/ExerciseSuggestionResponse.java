package groupeighteen.itufit.application.services.exercisesuggestion.suggestionrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ExerciseSuggestionResponse {
    private Long facilityId;
    private LocalDateTime startTime;
}
