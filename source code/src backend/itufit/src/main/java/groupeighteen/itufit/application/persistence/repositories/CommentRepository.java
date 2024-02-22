package groupeighteen.itufit.application.persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import groupeighteen.itufit.domain.comment.Comment;
import groupeighteen.itufit.domain.facility.Facility;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByFacility(Facility facility);
}
