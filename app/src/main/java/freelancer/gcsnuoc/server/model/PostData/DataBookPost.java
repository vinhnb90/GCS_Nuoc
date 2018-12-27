package freelancer.gcsnuoc.server.model.PostData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataBookPost {

@SerializedName("indexId")
@Expose
private String indexId;
@SerializedName("token")
@Expose
private String token;
@SerializedName("departmentId")
@Expose
private String departmentId;
@SerializedName("pointId")
@Expose
private String pointId;
@SerializedName("timeOfUse")
@Expose
private String timeOfUse;
@SerializedName("coefficient")
@Expose
private String coefficient;
@SerializedName("electricityMeterId")
@Expose
private String electricityMeterId;
@SerializedName("term")
@Expose
private String term;
@SerializedName("month")
@Expose
private String month;
@SerializedName("year")
@Expose
private String year;
@SerializedName("indexType")
@Expose
private String indexType;
@SerializedName("oldValue")
@Expose
private String oldValue;
@SerializedName("newValue")
@Expose
private String newValue;
@SerializedName("startDate")
@Expose
private String startDate;
@SerializedName("endDate")
@Expose
private String endDate;
@SerializedName("customerId")
@Expose
private String customerId;
@SerializedName("customerCode")
@Expose
private String customerCode;
@SerializedName("img")
@Expose
private String img;

public String getIndexId() {
return indexId;
}

public void setIndexId(String indexId) {
this.indexId = indexId;
}

public String getToken() {
return token;
}

public void setToken(String token) {
this.token = token;
}

public String getDepartmentId() {
return departmentId;
}

public void setDepartmentId(String departmentId) {
this.departmentId = departmentId;
}

public String getPointId() {
return pointId;
}

public void setPointId(String pointId) {
this.pointId = pointId;
}

public String getTimeOfUse() {
return timeOfUse;
}

public void setTimeOfUse(String timeOfUse) {
this.timeOfUse = timeOfUse;
}

public String getCoefficient() {
return coefficient;
}

public void setCoefficient(String coefficient) {
this.coefficient = coefficient;
}

public String getElectricityMeterId() {
return electricityMeterId;
}

public void setElectricityMeterId(String electricityMeterId) {
this.electricityMeterId = electricityMeterId;
}

public String getTerm() {
return term;
}

public void setTerm(String term) {
this.term = term;
}

public String getMonth() {
return month;
}

public void setMonth(String month) {
this.month = month;
}

public String getYear() {
return year;
}

public void setYear(String year) {
this.year = year;
}

public String getIndexType() {
return indexType;
}

public void setIndexType(String indexType) {
this.indexType = indexType;
}

public String getOldValue() {
return oldValue;
}

public void setOldValue(String oldValue) {
this.oldValue = oldValue;
}

public String getNewValue() {
return newValue;
}

public void setNewValue(String newValue) {
this.newValue = newValue;
}

public String getStartDate() {
return startDate;
}

public void setStartDate(String startDate) {
this.startDate = startDate;
}

public String getEndDate() {
return endDate;
}

public void setEndDate(String endDate) {
this.endDate = endDate;
}

public String getCustomerId() {
return customerId;
}

public void setCustomerId(String customerId) {
this.customerId = customerId;
}

public String getCustomerCode() {
return customerCode;
}

public void setCustomerCode(String customerCode) {
this.customerCode = customerCode;
}

public String getImg() {
return img;
}

public void setImg(String img) {
this.img = img;
}

}