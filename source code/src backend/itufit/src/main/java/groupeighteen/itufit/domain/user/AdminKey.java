package groupeighteen.itufit.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminKey {
    @Id
    private Long id;
    private byte[] keySalt;
    private byte[] keyHash;
}
