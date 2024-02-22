package groupeighteen.itufit.domain.academicprogram;

import groupeighteen.itufit.domain.user.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AcademicProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "academicProgram")
    private Student student;
    @OneToMany(mappedBy = "academicProgram")
    private List<Class> classes;
}
