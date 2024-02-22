package groupeighteen.itufit.application.persistence.repositories;

import groupeighteen.itufit.domain.academicprogram.AcademicProgram;
import groupeighteen.itufit.domain.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademicProgramRepository extends JpaRepository<AcademicProgram, Long> {
}
