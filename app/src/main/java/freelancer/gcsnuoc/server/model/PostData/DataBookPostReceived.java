package freelancer.gcsnuoc.server.model.PostData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataBookPostReceived {

@SerializedName("Result")
@Expose
private Boolean result;
@SerializedName("IsAuthor")
@Expose
private Boolean isAuthor;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("Data")
@Expose
private Object data;

public Boolean getResult() {
return result;
}

public void setResult(Boolean result) {
this.result = result;
}

public Boolean getIsAuthor() {
return isAuthor;
}

public void setIsAuthor(Boolean isAuthor) {
this.isAuthor = isAuthor;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Object getData() {
return data;
}

public void setData(Object data) {
this.data = data;
}

}