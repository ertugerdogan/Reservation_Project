package groupeighteen.itufit.application.services.comment;

import lombok.Getter;

@Getter
public class CommentAddRequest {
    private String comment;
    private Long facilityId;
    private Long userId;
}
