package freelancer.gcsnuoc.server.model.LoginServer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginPost {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("pass")
    @Expose
    private String pass;

    public LoginPost(String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}