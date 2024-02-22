package groupeighteen.itufit.application.services.academicprogram;

import groupeighteen.itufit.application.persistence.repositories.AcademicProgramRepository;
import groupeighteen.itufit.application.persistence.repositories.CommentRepository;
import groupeighteen.itufit.application.services.academicprogram.add.ClassDTO;
import groupeighteen.itufit.application.services.academicprogram.add.CreateAcademicProgramRequest;
import groupeighteen.itufit.application.services.academicprogram.delete.DeleteAcademicProgramRequest;
import groupeighteen.itufit.application.services.comment.CommentAddRequest;
import groupeighteen.itufit.application.services.comment.CommentListRequest;
import groupeighteen.itufit.application.services.comment.CommentListResponse;
import groupeighteen.itufit.application.services.comment.CommentService;
import groupeighteen.itufit.application.services.facility.FacilityService;
import groupeighteen.itufit.application.services.user.student.StudentService;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.application.shared.response.Response;
import groupeighteen.itufit.domain.academicprogram.AcademicProgram;
import groupeighteen.itufit.domain.academicprogram.Class;
import groupeighteen.itufit.domain.comment.Comment;
import groupeighteen.itufit.domain.facility.Facility;
import groupeighteen.itufit.domain.user.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AcademicProgramImp implements AcademicProgramService {
    private AcademicProgramRepository academicProgramRepository;
    private StudentService studentService;

    public AcademicProgramImp(AcademicProgramRepository academicProgramRepository, StudentService studentService) {
        this.academicProgramRepository = academicProgramRepository;
        this.studentService = studentService;
    }

    @Override
    public IResponse add(CreateAcademicProgramRequest createAcademicProgramRequest) {
        var academicProgramToCreate = new AcademicProgram();
        academicProgramToCreate.setStudent(studentService.findById(createAcademicProgramRequest.getStudentId()));
        var listOfClasses = new ArrayList<Class>();
        for (ClassDTO classDTO : createAcademicProgramRequest.getClassDTOS()) {
            var classToCreate = new Class();
            classToCreate.setDay(classDTO.getDay());
            classToCreate.setStartTime(classDTO.getStartTime());
            classToCreate.setFinishTime(classDTO.getFinishTime());
            classToCreate.setAcademicProgram(academicProgramToCreate);
            listOfClasses.add(classToCreate);
        }
        academicProgramToCreate.setClasses(listOfClasses);
        academicProgramRepository.save(academicProgramToCreate);
        return new Response<>(true, "sdasd");
    }

    private AcademicProgram findById(Long id) {
        var optionalAcademicProgram = academicProgramRepository.findById(id);
        if (optionalAcademicProgram.isEmpty())
            throw new RuntimeException();
        return optionalAcademicProgram.get();
    }

    @Override
    public IResponse delete(DeleteAcademicProgramRequest deleteAcademicProgramRequest) {
        academicProgramRepository.delete(this.findById(deleteAcademicProgramRequest.getAcademicProgramId()));
        return new Response<>(true, "asdas");
    }
}

