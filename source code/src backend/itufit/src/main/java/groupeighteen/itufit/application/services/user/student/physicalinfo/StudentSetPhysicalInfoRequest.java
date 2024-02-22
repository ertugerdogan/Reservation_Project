package groupeighteen.itufit.application.services.user.student.physicalinfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentSetPhysicalInfoRequest {
    private Long id;
    private double weight;
    private double goalWeight;
    private double height;
    private double basalMetabolism;
}
