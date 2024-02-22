package groupeighteen.itufit.application.services.user.student.physicalinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentGetPhysicalInfoRespond {
    private double weight;
    private double goalWeight;
    private double height;
    private double basalMetabolism;
}
