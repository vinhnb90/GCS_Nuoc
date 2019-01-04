package freelancer.gcsnuoc.entities;

public class SESSION {
    private int ID_TABLE_SESSION;
    private String USERNAME;
    private String PASSWORD;
    private String DATE_LOGIN;
    private String NAME_NVIEN;
    private String MA_NVIEN;

    public SESSION(String USERNAME, String PASSWORD, String DATE_LOGIN, String NAME_NVIEN, String MA_NVIEN) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.DATE_LOGIN = DATE_LOGIN;
        this.NAME_NVIEN = NAME_NVIEN;
        this.MA_NVIEN = MA_NVIEN;
    }

    public SESSION() {
    }

    public int getID_TABLE_SESSION() {
        return ID_TABLE_SESSION;
    }

    public void setID_TABLE_SESSION(int ID_TABLE_SESSION) {
        this.ID_TABLE_SESSION = ID_TABLE_SESSION;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getDATE_LOGIN() {
        return DATE_LOGIN;
    }

    public void setDATE_LOGIN(String DATE_LOGIN) {
        this.DATE_LOGIN = DATE_LOGIN;
    }

    public String getMA_NVIEN() {
        return MA_NVIEN;
    }

    public void setMA_NVIEN(String MA_NVIEN) {
        this.MA_NVIEN = MA_NVIEN;
    }

    public String getNAME_NVIEN() {
        return NAME_NVIEN;
    }

    public void setNAME_NVIEN(String NAME_NVIEN) {
        this.NAME_NVIEN = NAME_NVIEN;
    }
}
