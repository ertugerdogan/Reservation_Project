package groupeighteen.itufit.application.services.comment;

import java.util.List;

import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.domain.comment.Comment;

public interface CommentService {

    IResponse add(CommentAddRequest commentAddRequest);

    DataResponse <List<CommentListResponse>> list(CommentListRequest commentListRequest);
    
}
