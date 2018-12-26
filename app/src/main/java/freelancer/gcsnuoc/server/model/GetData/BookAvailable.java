package freelancer.gcsnuoc.server.model.GetData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookAvailable {

@SerializedName("CalendarOfSaveIndexId")
@Expose
private Integer calendarOfSaveIndexId;
@SerializedName("DepartmentId")
@Expose
private Integer departmentId;
@SerializedName("FigureBookId")
@Expose
private Integer figureBookId;
@SerializedName("Term")
@Expose
private Integer term;
@SerializedName("Month")
@Expose
private Integer month;
@SerializedName("Year")
@Expose
private Integer year;
@SerializedName("StartDate")
@Expose
private String startDate;
@SerializedName("EndDate")
@Expose
private String endDate;
@SerializedName("Status")
@Expose
private Integer status;
@SerializedName("SaveDate")
@Expose
private Object saveDate;
@SerializedName("CreateDate")
@Expose
private String createDate;
@SerializedName("CreateUser")
@Expose
private Integer createUser;
@SerializedName("BookCode")
@Expose
private String bookCode;
@SerializedName("BookName")
@Expose
private String bookName;
@SerializedName("IsBillCalculator")
@Expose
private Boolean isBillCalculator;
@SerializedName("CountBill")
@Expose
private Integer countBill;
@SerializedName("SumElectricityIndex")
@Expose
private Double sumElectricityIndex;
@SerializedName("Total")
@Expose
private Double total;
@SerializedName("DepartmentCode")
@Expose
private Object departmentCode;

public Integer getCalendarOfSaveIndexId() {
return calendarOfSaveIndexId;
}

public void setCalendarOfSaveIndexId(Integer calendarOfSaveIndexId) {
this.calendarOfSaveIndexId = calendarOfSaveIndexId;
}

public Integer getDepartmentId() {
return departmentId;
}

public void setDepartmentId(Integer departmentId) {
this.departmentId = departmentId;
}

public Integer getFigureBookId() {
return figureBookId;
}

public void setFigureBookId(Integer figureBookId) {
this.figureBookId = figureBookId;
}

public Integer getTerm() {
return term;
}

public void setTerm(Integer term) {
this.term = term;
}

public Integer getMonth() {
return month;
}

public void setMonth(Integer month) {
this.month = month;
}

public Integer getYear() {
return year;
}

public void setYear(Integer year) {
this.year = year;
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

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public Object getSaveDate() {
return saveDate;
}

public void setSaveDate(Object saveDate) {
this.saveDate = saveDate;
}

public String getCreateDate() {
return createDate;
}

public void setCreateDate(String createDate) {
this.createDate = createDate;
}

public Integer getCreateUser() {
return createUser;
}

public void setCreateUser(Integer createUser) {
this.createUser = createUser;
}

public String getBookCode() {
return bookCode;
}

public void setBookCode(String bookCode) {
this.bookCode = bookCode;
}

public String getBookName() {
return bookName;
}

public void setBookName(String bookName) {
this.bookName = bookName;
}

public Boolean getIsBillCalculator() {
return isBillCalculator;
}

public void setIsBillCalculator(Boolean isBillCalculator) {
this.isBillCalculator = isBillCalculator;
}

public Integer getCountBill() {
return countBill;
}

public void setCountBill(Integer countBill) {
this.countBill = countBill;
}

public Double getSumElectricityIndex() {
return sumElectricityIndex;
}

public void setSumElectricityIndex(Double sumElectricityIndex) {
this.sumElectricityIndex = sumElectricityIndex;
}

public Double getTotal() {
return total;
}

public void setTotal(Double total) {
this.total = total;
}

public Object getDepartmentCode() {
return departmentCode;
}

public void setDepartmentCode(Object departmentCode) {
this.departmentCode = departmentCode;
}

}