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
    private Timestamp resDate;

    // ✅ 추가 필드 (요금)
    private int amount;

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

    public Timestamp getResDate() { return resDate; }
    public void setResDate(Timestamp resDate) { this.resDate = resDate; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    @Override
    public String toString() {
        return "Reservation [resId=" + resId + ", custId=" + custId + ", trainNo=" + trainNo +
               ", deptStation=" + deptStation + ", arriStation=" + arriStation +
               ", deptTime=" + deptTime + ", arriTime=" + arriTime +
               ", resDate=" + resDate + ", amount=" + amount + "]";
    }
}
