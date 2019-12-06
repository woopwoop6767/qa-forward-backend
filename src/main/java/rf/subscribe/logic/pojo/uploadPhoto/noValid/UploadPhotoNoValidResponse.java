package rf.subscribe.logic.pojo.uploadPhoto.noValid;

public class UploadPhotoNoValidResponse{
	private Object resultData;
	private String errorMessage;
	private int errorCode;

	public Object getResultData(){
		return resultData;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public int getErrorCode(){
		return errorCode;
	}

	@Override
 	public String toString(){
		return 
			"UploadPhotoNoValidResponse{" + 
			"resultData = '" + resultData + '\'' + 
			",errorMessage = '" + errorMessage + '\'' + 
			",errorCode = '" + errorCode + '\'' + 
			"}";
		}
}
