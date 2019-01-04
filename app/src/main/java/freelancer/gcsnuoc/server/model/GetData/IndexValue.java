package freelancer.gcsnuoc.server.model.GetData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndexValue {

    @SerializedName("IndexId")
    @Expose
    private Double indexId;
    @SerializedName("DepartmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("PointId")
    @Expose
    private Integer pointId;
    @SerializedName("TimeOfUse")
    @Expose
    private String timeOfUse;
    @SerializedName("Coefficient")
    @Expose
    private Double coefficient;
    @SerializedName("ElectricityMeterId")
    @Expose
    private Integer electricityMeterId;
    @SerializedName("Term")
    @Expose
    private Integer term;
    @SerializedName("Month")
    @Expose
    private Integer month;
    @SerializedName("Year")
    @Expose
    private Integer year;
    @SerializedName("IndexType")
    @Expose
    private String indexType;
    @SerializedName("OldValue")
    @Expose
    private Double oldValue;
    @SerializedName("NewValue")
    @Expose
    private Double newValue;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("CreateDate")
    @Expose
    private Object createDate;
    @SerializedName("CreateUser")
    @Expose
    private Integer createUser;
    @SerializedName("ElectricityMeterCode")
    @Expose
    private Object electricityMeterCode;
    @SerializedName("ElectricityMeterNumber")
    @Expose
    private Object electricityMeterNumber;
    @SerializedName("PointCode")
    @Expose
    private String pointCode;
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("FigureBookId")
    @Expose
    private Integer figureBookId;
    @SerializedName("PrevQuantity")
    @Expose
    private Double PrevQuantity;
    @SerializedName("BookName")
    @Expose
    private Object bookName;
    @SerializedName("ContractCode")
    @Expose
    private Object contractCode;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("NewValueLastMonth")
    @Expose
    private Object newValueLastMonth;
    @SerializedName("OldValueLastMonth")
    @Expose
    private Object oldValueLastMonth;
    @SerializedName("CoefficientLastMonth")
    @Expose
    private Object coefficientLastMonth;
    @SerializedName("Index")
    @Expose
    private Integer index;
    @SerializedName("Img")
    @Expose
    private Object img;

    public Double getIndexId() {
        return indexId;
    }

    public void setIndexId(Double indexId) {
        this.indexId = indexId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    public String getTimeOfUse() {
        return timeOfUse;
    }

    public void setTimeOfUse(String timeOfUse) {
        this.timeOfUse = timeOfUse;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public Integer getElectricityMeterId() {
        return electricityMeterId;
    }

    public void setElectricityMeterId(Integer electricityMeterId) {
        this.electricityMeterId = electricityMeterId;
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

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public Double getOldValue() {
        return oldValue;
    }

    public void setOldValue(Double oldValue) {
        this.oldValue = oldValue;
    }

    public Double getNewValue() {
        return newValue;
    }

    public void setNewValue(Double newValue) {
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Object getElectricityMeterCode() {
        return electricityMeterCode;
    }

    public void setElectricityMeterCode(Object electricityMeterCode) {
        this.electricityMeterCode = electricityMeterCode;
    }

    public Object getElectricityMeterNumber() {
        return electricityMeterNumber;
    }

    public void setElectricityMeterNumber(Object electricityMeterNumber) {
        this.electricityMeterNumber = electricityMeterNumber;
    }

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFigureBookId() {
        return figureBookId;
    }

    public void setFigureBookId(Integer figureBookId) {
        this.figureBookId = figureBookId;
    }

    public Object getBookName() {
        return bookName;
    }

    public void setBookName(Object bookName) {
        this.bookName = bookName;
    }

    public Object getContractCode() {
        return contractCode;
    }

    public void setContractCode(Object contractCode) {
        this.contractCode = contractCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getNewValueLastMonth() {
        return newValueLastMonth;
    }

    public void setNewValueLastMonth(Object newValueLastMonth) {
        this.newValueLastMonth = newValueLastMonth;
    }

    public Object getOldValueLastMonth() {
        return oldValueLastMonth;
    }

    public void setOldValueLastMonth(Object oldValueLastMonth) {
        this.oldValueLastMonth = oldValueLastMonth;
    }

    public Object getCoefficientLastMonth() {
        return coefficientLastMonth;
    }

    public void setCoefficientLastMonth(Object coefficientLastMonth) {
        this.coefficientLastMonth = coefficientLastMonth;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public Double getPrevQuantity() {
        return PrevQuantity;
    }

    public void setPrevQuantity(Double prevQuantity) {
        PrevQuantity = prevQuantity;
    }
}