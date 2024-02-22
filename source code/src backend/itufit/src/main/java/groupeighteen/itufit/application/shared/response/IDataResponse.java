package groupeighteen.itufit.application.shared.response;

public interface IDataResponse<TData> extends IResponse {
    TData getData();
}
