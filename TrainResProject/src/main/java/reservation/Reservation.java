package reservation;

import java.sql.Timestamp;

public class Reservation {
    private int resId;
    private String custId;
    private String trainNo;
    private String deptStation;
    private String arriStation;
    private Timestamp deptTime;
    private Timestamp arriTime;
    
    // 기존 Timestamp 필드는 유지
    private Timestamp resDate; 

    // 🌟 DAO에서 포맷팅된 문자열(YYYY-MM-DD HH:MI)을 받기 위한 새로운 필드
    private String formattedResDate; 

    private int amount;
    private Integer carNo;
    private String seatNo;

    public int getResId() { return resId; }
    public void setResId(int resId) { this.resId = resId; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }

    public String getTrainNo() { return trainNo; }
    public void setTrainNo(String trainNo) { this.trainNo = trainNo; }

    public String getDeptStation() { return deptStation; }
    public void setDeptStation(String deptStation) { this.deptStation = deptStation; }

    public String getArriStation() { return arriStation; }
    public void setArriStation(String arriStation) { this.arriStation = arriStation; }

    public Timestamp getDeptTime() { return deptTime; }
    public void setDeptTime(Timestamp deptTime) { this.deptTime = deptTime; }

    public Timestamp getArriTime() { return arriTime; }
    public void setArriTime(Timestamp arriTime) { this.arriTime = arriTime; }

    // 기존 Timestamp Getter/Setter 유지
    public Timestamp getResDate() { return resDate; }
    public void setResDate(Timestamp resDate) { this.resDate = resDate; }

    // 🌟 DAO에서 사용될 Setter
    public void setFormattedResDate(String formattedResDate) {
        this.formattedResDate = formattedResDate;
    }

    // 🌟 JSP에서 사용될 Getter
    public String getFormattedResDate() {
        return formattedResDate;
    }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public Integer getCarNo() { return carNo; }
    public void setCarNo(Integer carNo) { this.carNo = carNo; }

    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }
}