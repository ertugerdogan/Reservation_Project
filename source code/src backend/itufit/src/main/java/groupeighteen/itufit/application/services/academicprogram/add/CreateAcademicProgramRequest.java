package groupeighteen.itufit.application.services.academicprogram.add;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateAcademicProgramRequest {
    private Long studentId;
    private List<ClassDTO> classDTOS;
}
