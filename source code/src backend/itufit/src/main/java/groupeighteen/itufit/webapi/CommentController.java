package groupeighteen.itufit.webapi;

import org.springframework.web.bind.annotation.RestController;

import groupeighteen.itufit.application.services.comment.CommentAddRequest;
import groupeighteen.itufit.application.services.comment.CommentListRequest;
import groupeighteen.itufit.application.services.comment.CommentListResponse;
import groupeighteen.itufit.application.services.comment.CommentService;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.domain.comment.Comment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @CrossOrigin
    @PostMapping(value = "addComment", produces = "application/json")
    public IResponse add(@RequestBody CommentAddRequest commentAddRequest) {
        return commentService.add(commentAddRequest);
    }
    @CrossOrigin
    @PostMapping(value = "listComment", produces = "application/json")
    public DataResponse <List<CommentListResponse>> list(@RequestBody CommentListRequest commentListRequest) {
        return commentService.list(commentListRequest);
    }
    
}
