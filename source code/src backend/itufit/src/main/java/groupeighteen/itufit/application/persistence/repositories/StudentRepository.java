package groupeighteen.itufit.application.persistence.repositories;

import groupeighteen.itufit.domain.user.Admin;
import groupeighteen.itufit.domain.user.Student;
import groupeighteen.itufit.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findById(Long id);
    Optional<Student> findByEmail(String email);

    List<Student> findAllByFirstName(String firstName);
    // List<Student> findTop10ByOrderExerciseScoreDesc();
}
