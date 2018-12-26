package freelancer.gcsnuoc.server.model.GetData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookInfoPost {
    @SerializedName("empId")
    @Expose
    private String empId;
    @SerializedName("token")
    @Expose
    private String token;

    public BookInfoPost(String empId, String token) {
        this.empId = empId;
        this.token = token;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}