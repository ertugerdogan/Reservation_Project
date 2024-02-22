package groupeighteen.itufit.application.services.user.student;

import groupeighteen.itufit.application.persistence.repositories.StudentRepository;
import groupeighteen.itufit.application.security.HashService;
import groupeighteen.itufit.application.security.JwtService;
import groupeighteen.itufit.application.services.user.student.changepassword.StudentPasswordChangeRequest;
import groupeighteen.itufit.application.services.user.student.login.StudentLoginRequest;
import groupeighteen.itufit.application.services.user.student.login.StudentLoginResponse;
import groupeighteen.itufit.application.services.user.student.physicalinfo.StudentGetPhysicalInfoRequest;
import groupeighteen.itufit.application.services.user.student.physicalinfo.StudentGetPhysicalInfoRespond;
import groupeighteen.itufit.application.services.user.student.physicalinfo.StudentSetPhysicalInfoRequest;
import groupeighteen.itufit.application.services.user.student.ranking.StudentRankingResponse;
import groupeighteen.itufit.application.services.user.student.register.StudentRegisterRequest;

import groupeighteen.itufit.application.services.user.student.restrict.StudentListRestrictedResponse;
import groupeighteen.itufit.application.services.user.student.restrict.StudentRestrictRequest;
import groupeighteen.itufit.application.services.user.student.search.StudentSearchRequest;
import groupeighteen.itufit.application.services.user.student.search.StudentSearchResponse;
import groupeighteen.itufit.application.shared.response.DataResponse;
import groupeighteen.itufit.application.shared.response.IDataResponse;
import groupeighteen.itufit.application.shared.response.IResponse;
import groupeighteen.itufit.application.shared.response.Response;
import groupeighteen.itufit.domain.user.Student;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;

@Service
public class StudentServiceImp implements UserDetailsService, StudentService {
    private StudentRepository studentRepository;
    private HashService hashService;
    private JwtService jwtService;

    public StudentServiceImp(StudentRepository studentRepository, HashService hashService, JwtService jwtService) {
        this.studentRepository = studentRepository;
        this.hashService = hashService;
        this.jwtService = jwtService;
    }

    public IDataResponse<StudentLoginResponse> login(StudentLoginRequest studentLoginRequest) {
        var optionalStudent = studentRepository.findByEmail(studentLoginRequest.getEmail());
        if (optionalStudent.isEmpty()) {
            var response = new DataResponse<StudentLoginResponse>(
                    false,
                    "Email or password incorrect!",
                    new StudentLoginResponse(null, null));
            return response;
        }

        var student = optionalStudent.get();
        var hashedPassword = hashService.hashPassword(studentLoginRequest.getPassword(), student.getPasswordSalt());
        if (Arrays.equals(hashedPassword, student.getPasswordHash())) {

            var response = new DataResponse<StudentLoginResponse>(
                    true,
                    "",
                    new StudentLoginResponse(jwtService.generateToken(student.getEmail()), student.getId()));

            return response;
        }
        var response = new DataResponse<StudentLoginResponse>(
                false,
                "Email or password incorrect!",
                new StudentLoginResponse(null, null));
        return response;
    }

    @Override
    public IResponse setPhysicalInfo(StudentSetPhysicalInfoRequest studentSetPhysicalInfoRequest) {
        Long id = studentSetPhysicalInfoRequest.getId();
        // var optionalStudent = this.findById(id);
        // if(optionalStudent.isEmpty()){
        //     throw new RuntimeException();
        // }
        Student student = this.findById(id);
        student.setWeight(studentSetPhysicalInfoRequest.getWeight());
        student.setHeight(studentSetPhysicalInfoRequest.getHeight());
        student.setGoalWeight(studentSetPhysicalInfoRequest.getGoalWeight());
        student.setBasalMetabolism(studentSetPhysicalInfoRequest.getBasalMetabolism());

        studentRepository.save(student);
        return new Response<>(true, "");
    }

    public IDataResponse<StudentGetPhysicalInfoRespond> getPhysicalInfo(StudentGetPhysicalInfoRequest studentGetPhysicalInfoRequest) {
        Long id = studentGetPhysicalInfoRequest.getUserId();
        Student student = this.findById(id);

        var response = new DataResponse<StudentGetPhysicalInfoRespond>(
                true,
                "Physical Info returned",
                new StudentGetPhysicalInfoRespond(student.getWeight(),
                        student.getGoalWeight(),
                        student.getHeight(),
                        student.getBasalMetabolism()));
        return response;
    }

    public IResponse register(StudentRegisterRequest studentRegisterRequest) {
        var optionalStudent = studentRepository.findByEmail(studentRegisterRequest.getEmail());
        if (optionalStudent.isPresent())
            return new Response<>(false, "User already exists");

        Student studentToRegister = new Student();
        studentToRegister.setEmail(studentRegisterRequest.getEmail());
        studentToRegister.setUserName(extractUsername(studentRegisterRequest.getEmail()));
        studentToRegister.setFirstName(studentRegisterRequest.getFirstName());
        studentToRegister.setLastName(studentRegisterRequest.getLastName());
        studentToRegister.setPasswordSalt(hashService.saltPassword(studentRegisterRequest.getPassword()));
        studentToRegister.setPasswordHash(hashService.hashPassword(studentRegisterRequest.getPassword(), studentToRegister.getPasswordSalt()));

        studentRepository.save(studentToRegister);
        return new Response<>(true, "");
    }

    public DataResponse<List<StudentRankingResponse>> listRankings() {
        // List<Student> students = studentRepository.findTop10ByOrderExerciseScoreDesc();
        List<Student> students = studentRepository.findAll(Sort.by(Sort.Direction.DESC, "exerciseScore"));

        List<StudentRankingResponse> rankingResponses = new ArrayList<>();
        Integer i = 0;
        for (Student student : students) {

            StudentRankingResponse aStudent = new StudentRankingResponse(student.getFirstName(), student.getExerciseScore());
            rankingResponses.add(aStudent);
            i++;
            if (i == 7) {
                break;
            }
        }

        var response = new DataResponse<List<StudentRankingResponse>>(true, "", rankingResponses);
        return response;
    }

    public IResponse restrict(StudentRestrictRequest studentRestrictRequest) {
        Student student = this.findById(studentRestrictRequest.getId());
        student.setRestricted(!student.isRestricted());
        studentRepository.save(student);
        return new Response<>(true, "User is restricted");
    }

    public DataResponse<List<StudentListRestrictedResponse>> listRestrict() {
        List<Student> students = studentRepository.findAll();
        List<StudentListRestrictedResponse> responseStudents = new ArrayList<>();

        for (Student student : students) {
            if (student.isRestricted()) {
                StudentListRestrictedResponse aResponse = new StudentListRestrictedResponse(student.getId(), student.getFirstName(), student.getLastName());
                responseStudents.add(aResponse);
            }
        }

        var response = new DataResponse<List<StudentListRestrictedResponse>>(true, "", responseStudents);
        return response;
    }

    public DataResponse<List<StudentSearchResponse>> search(StudentSearchRequest studentSearchRequest) {
        String fullName = studentSearchRequest.getName();
        String[] names = fullName.split(" ");
        String firstName = names[0];
        String lastName = names[1];
        List<StudentSearchResponse> responseStudents = new ArrayList<>();


        List<Student> students = studentRepository.findAllByFirstName(firstName);
        Student selectedSudent = new Student();
        Boolean isFound = false;

        for (Student student : students) {
            if (student.getLastName().equals(lastName)) {
                selectedSudent = student;
                isFound = true;
                break;
            }
        }

        StudentSearchResponse aResponse = new StudentSearchResponse(selectedSudent.getId(), selectedSudent.getFirstName(), selectedSudent.getLastName());
        responseStudents.add(aResponse);

        var response = new DataResponse<List<StudentSearchResponse>>(isFound, "", responseStudents);
        return response;
    }

    public void increaseScore(Long id) {
        Student student = this.findById(id);
        student.setExerciseScore(student.getExerciseScore() + 2);
        studentRepository.save(student);
    }

    public void decreaseScore(Long id) {
        Student student = this.findById(id);
        student.setExerciseScore(student.getExerciseScore() - 2);
        studentRepository.save(student);
    }

    public Student findByEmail(String email) {
        var optionalStudent = studentRepository.findByEmail(email);
        if (optionalStudent.isEmpty())
            throw new RuntimeException();
        return optionalStudent.get();
    }

    public Student findById(Long id) {
        var optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty())
            throw new RuntimeException();
        return optionalStudent.get();
    }

    @Override
    public IResponse changePassword(StudentPasswordChangeRequest studentPasswordChangeRequest) {
        if (!Objects.equals(studentPasswordChangeRequest.getNewPassword(), studentPasswordChangeRequest.getNewPasswordConfirmation()))
            throw new RuntimeException("sifreler ayni degil");
        var student = this.findById(studentPasswordChangeRequest.getUserId());

        var oldPasswordSalt = student.getPasswordSalt();
        var oldPasswordHash = hashService.hashPassword(studentPasswordChangeRequest.getOldPassword(), oldPasswordSalt);
        if (!Arrays.equals(oldPasswordHash, student.getPasswordHash()))
            throw new RuntimeException("old password wrong");
        student.setPasswordSalt(hashService.saltPassword(studentPasswordChangeRequest.getNewPassword()));
        student.setPasswordHash(hashService.hashPassword(studentPasswordChangeRequest.getNewPassword(), student.getPasswordSalt()));
        studentRepository.save(student);
        return new Response<>(true, "Password changed");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return this.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String extractUsername(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
