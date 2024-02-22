package groupeighteen.itufit.domain.academicprogram;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Day day;
    private LocalTime startTime;
    private LocalTime finishTime;
    @ManyToOne
    private AcademicProgram academicProgram;
}
