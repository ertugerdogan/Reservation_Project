package groupeighteen.itufit.application.shared.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response<TData> implements IResponse{
    private boolean success;
    private String message;
}
