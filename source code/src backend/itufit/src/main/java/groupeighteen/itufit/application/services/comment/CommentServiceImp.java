package groupeighteen.itufit.application.services.comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import groupeighteen.itufit.application.persistence.repositories.CommentRepository;
import groupeighteen.itufit.application.services.facility.FacilityService;
import groupeighteen.itufit.application.services.user.student.StudentService;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.application.shared.response.Response;
import groupeighteen.itufit.domain.comment.Comment;
import groupeighteen.itufit.domain.facility.Facility;
import groupeighteen.itufit.domain.user.Student;

@Service
public class CommentServiceImp implements CommentService {
    private CommentRepository commentRepository;
    private FacilityService facilityService;
    private StudentService studentService;

    public CommentServiceImp(CommentRepository commentRepository, FacilityService facilityService, StudentService studentService){
        this.commentRepository = commentRepository;
        this.facilityService = facilityService;
        this.studentService = studentService;
    }

    public IResponse add(CommentAddRequest commentAddRequest){

        LocalDateTime commentDate = LocalDateTime.now();
        Facility facilityToComment = facilityService.findById(commentAddRequest.getFacilityId());
        Student studentToComment = studentService.findById(commentAddRequest.getUserId());

        Comment commentToAdd = new Comment();
        commentToAdd.setComment(commentAddRequest.getComment());
        commentToAdd.setDate(commentDate);
        commentToAdd.setFacility(facilityToComment);
        commentToAdd.setStudent(studentToComment);

        commentRepository.save(commentToAdd);

        return new Response<>(true, "");
    }

    public DataResponse <List<CommentListResponse>> list(CommentListRequest commentListRequest){
        
        Facility facilityToList = facilityService.findById(commentListRequest.getFacilityId());

        List <Comment> comments = commentRepository.findByFacility(facilityToList);
        List <CommentListResponse> commentIds = new ArrayList<>();

        for(Comment comment:comments){
            CommentListResponse aComment = new CommentListResponse(comment.getComment(), comment.getStudent().getFirstName());
            commentIds.add(aComment);
        }
        var response = new DataResponse<List<CommentListResponse>>(
                    true,
                    "",
                    commentIds);

        return response;
    }
}

