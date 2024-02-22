package groupeighteen.itufit.domain.user;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin extends User{
    private int facilityId;
}
