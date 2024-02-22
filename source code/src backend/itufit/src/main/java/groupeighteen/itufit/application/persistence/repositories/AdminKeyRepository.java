package groupeighteen.itufit.application.persistence.repositories;

import groupeighteen.itufit.domain.user.Admin;
import groupeighteen.itufit.domain.user.AdminKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminKeyRepository extends JpaRepository<AdminKey, Long> {

}

