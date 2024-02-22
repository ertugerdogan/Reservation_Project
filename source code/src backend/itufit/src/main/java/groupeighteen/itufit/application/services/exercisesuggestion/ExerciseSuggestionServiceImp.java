package groupeighteen.itufit.application.services.exercisesuggestion;

import groupeighteen.itufit.application.persistence.repositories.FacilityRepository;
import groupeighteen.itufit.application.services.academicprogram.AcademicProgramService;
import groupeighteen.itufit.application.services.exercisesuggestion.suggestionrequest.ExerciseSuggestionRequest;
import groupeighteen.itufit.application.services.exercisesuggestion.suggestionrequest.ExerciseSuggestionResponse;
import groupeighteen.itufit.application.services.facility.*;
import groupeighteen.itufit.application.services.meal.MealService;
import groupeighteen.itufit.application.services.reservation.ReservationService;
import groupeighteen.itufit.application.services.user.student.StudentService;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.application.shared.response.Response;
import groupeighteen.itufit.domain.academicprogram.Class;
import groupeighteen.itufit.domain.facility.Facility;
import groupeighteen.itufit.domain.facility.FacilityType;
import groupeighteen.itufit.domain.reservation.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// import groupeighteen.itufit.domain.user.User;


@Service
public class ExerciseSuggestionServiceImp implements ExerciseSuggestionService {
    private StudentService studentService;
    private MealService mealService;
    private ReservationService reservationService;
    private FacilityService facilityService;

    public ExerciseSuggestionServiceImp(StudentService studentService, MealService mealService, ReservationService reservationService, FacilityService facilityService) {
        this.studentService = studentService;
        this.mealService = mealService;
        this.reservationService = reservationService;
        this.facilityService = facilityService;
    }

    @Override
    public IDataResponse<List<ExerciseSuggestionResponse>> suggestExercise(ExerciseSuggestionRequest exerciseSuggestionRequest) {

        var exerciseSuggestions = new ArrayList<ExerciseSuggestionResponse>();
        var student = studentService.findById(exerciseSuggestionRequest.getStudentId());
        double caloriesConsumed = (mealService.getCalorie() + 300) * 7;
        double caloriesBurned = 0.0;
        caloriesBurned += student.getBasalMetabolism() * 7 * 5 / 4;

        Random rand = new Random();

        while (true) {
            if (student.getWeight() > student.getGoalWeight() + 1) {
                if (caloriesBurned > caloriesConsumed + 7000)
                    break;
            } else if (student.getWeight() + 1 < student.getGoalWeight()) {
                break;
            } else {
                if (caloriesBurned > caloriesConsumed)
                    break;
            }

            int facilityType = rand.nextInt(7);
            var localDateTime = LocalDateTime.now();
            var year = localDateTime.getYear();
            var month = localDateTime.getMonthValue();
            var dayOfMonth = localDateTime.getDayOfMonth();
            var hour = rand.nextInt(21 - 8 + 1) + 8;
            int randDay = rand.nextInt(7);
            var suggestDate = LocalDateTime.of(year, month, dayOfMonth + randDay, hour, 0);
            var facility = facilityService.findByFacilityType(FacilityType.values()[facilityType]);

            if (reservationService.isSessionAvailable(facility.getId(), suggestDate)) {
                boolean add = true;
                for (Class classs : student.getAcademicProgram().getClasses()) {
                    if (classs.getDay().name().equals(suggestDate.getDayOfWeek().name())
                            && (suggestDate.getHour() >= classs.getStartTime().getHour() && suggestDate.getHour() < classs.getFinishTime().getHour())) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    caloriesBurned += facility.getCalorie();
                    exerciseSuggestions.add(new ExerciseSuggestionResponse(facility.getId(), suggestDate));
                }
            }
        }
        return new DataResponse<>(true, "sad", exerciseSuggestions);
    }
}
