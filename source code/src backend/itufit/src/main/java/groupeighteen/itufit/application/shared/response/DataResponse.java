package groupeighteen.itufit.application.shared.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<TData> extends Response implements IDataResponse<TData>{

    public DataResponse(Boolean success, String message, TData data){
        super(success, message);
        this.data = data;
    }
    private TData data;
}
