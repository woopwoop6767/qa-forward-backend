
package rf.subscribe.logic.api.model.categories;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriesModel {
    private Integer errorCode;
    private Object errorMessage;
    private ResultData resultData;

    @JsonProperty("errorCode")
    private void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
    public Integer getErrorCode() {
        return errorCode;
    }

    @JsonProperty("errorMessage")
    private void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }
    public Object getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("resultData")
    private void setResultData(ResultData resultData) {
        this.resultData = resultData;
    }
    public ResultData getResultData() {
        return resultData;
    }
}
