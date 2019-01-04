package freelancer.gcsnuoc.server.model.LoginServer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("EmpId")
@Expose
private Integer empId;
@SerializedName("FullName")
@Expose
private String fullName;
@SerializedName("UserName")
@Expose
private Object userName;
@SerializedName("Password")
@Expose
private Object password;
@SerializedName("Email")
@Expose
private Object email;
@SerializedName("PhoneNumber")
@Expose
private Object phoneNumber;
@SerializedName("GenderId")
@Expose
private Integer genderId;
@SerializedName("DepartmentId")
@Expose
private Integer departmentId;
@SerializedName("ConfirmPassword")
@Expose
private Object confirmPassword;

public Integer getEmpId() {
return empId;
}

public void setEmpId(Integer empId) {
this.empId = empId;
}

public String getFullName() {
return fullName;
}

public void setFullName(String fullName) {
this.fullName = fullName;
}

public Object getUserName() {
return userName;
}

public void setUserName(Object userName) {
this.userName = userName;
}

public Object getPassword() {
return password;
}

public void setPassword(Object password) {
this.password = password;
}

public Object getEmail() {
return email;
}

public void setEmail(Object email) {
this.email = email;
}

public Object getPhoneNumber() {
return phoneNumber;
}

public void setPhoneNumber(Object phoneNumber) {
this.phoneNumber = phoneNumber;
}

public Integer getGenderId() {
return genderId;
}

public void setGenderId(Integer genderId) {
this.genderId = genderId;
}

public Integer getDepartmentId() {
return departmentId;
}

public void setDepartmentId(Integer departmentId) {
this.departmentId = departmentId;
}

public Object getConfirmPassword() {
return confirmPassword;
}

public void setConfirmPassword(Object confirmPassword) {
this.confirmPassword = confirmPassword;
}

}