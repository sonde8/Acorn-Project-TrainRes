package payment;

public class MyTicketDTO {

    private int driveId;
    private String trainNo;
    private String deptName;
    private String arriName;
    private String deptTime;
    private String arriTime;
    private int price;

    public int getDriveId() { return driveId; }
    public void setDriveId(int driveId) { this.driveId = driveId; }

    public String getTrainNo() { return trainNo; }
    public void setTrainNo(String trainNo) { this.trainNo = trainNo; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getArriName() { return arriName; }
    public void setArriName(String arriName) { this.arriName = arriName; }

    public String getDeptTime() { return deptTime; }
    public void setDeptTime(String deptTime) { this.deptTime = deptTime; }

    public String getArriTime() { return arriTime; }
    public void setArriTime(String arriTime) { this.arriTime = arriTime; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}
