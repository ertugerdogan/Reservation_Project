package groupeighteen.itufit.application.services.academicprogram.add;

import groupeighteen.itufit.domain.academicprogram.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ClassDTO {
    private Day day;
    private LocalTime startTime;
    private LocalTime finishTime;
}
