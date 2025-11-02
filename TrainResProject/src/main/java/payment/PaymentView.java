package payment;

public class PaymentView {

    private long driveId;
    private String deptStationName;
    private String arriStationName;
    private String trainNo;
    private String deptTime;
    private String arriTime;
    private int price;

    public long getDriveId() { return driveId; }
    public void setDriveId(long driveId) { this.driveId = driveId; }

    public String getDeptStationName() { return deptStationName; }
    public void setDeptStationName(String deptStationName) { this.deptStationName = deptStationName; }

    public String getArriStationName() { return arriStationName; }
    public void setArriStationName(String arriStationName) { this.arriStationName = arriStationName; }

    public String getTrainNo() { return trainNo; }
    public void setTrainNo(String trainNo) { this.trainNo = trainNo; }

    public String getDeptTime() { return deptTime; }
    public void setDeptTime(String deptTime) { this.deptTime = deptTime; }

    public String getArriTime() { return arriTime; }
    public void setArriTime(String arriTime) { this.arriTime = arriTime; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}
