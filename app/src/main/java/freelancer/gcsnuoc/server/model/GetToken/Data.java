package freelancer.gcsnuoc.server.model.GetToken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("token")
@Expose
private String token;
@SerializedName("expiryDate")
@Expose
private String expiryDate;

public String getToken() {
return token;
}

public void setToken(String token) {
this.token = token;
}

public String getExpiryDate() {
return expiryDate;
}

public void setExpiryDate(String expiryDate) {
this.expiryDate = expiryDate;
}

}