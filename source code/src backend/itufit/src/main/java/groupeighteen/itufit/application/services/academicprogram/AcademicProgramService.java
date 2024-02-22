package groupeighteen.itufit.application.services.academicprogram;

import groupeighteen.itufit.application.services.academicprogram.add.CreateAcademicProgramRequest;
import groupeighteen.itufit.application.services.academicprogram.delete.DeleteAcademicProgramRequest;
import groupeighteen.itufit.application.shared.response.IResponse;

public interface AcademicProgramService {
    IResponse add(CreateAcademicProgramRequest createAcademicProgramRequest);
    IResponse delete(DeleteAcademicProgramRequest deleteAcademicProgramRequest);
}
